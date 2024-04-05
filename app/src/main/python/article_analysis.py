import requests
from google.colab import userdata

OPENAI_API_KEY = userdata.get('OPENAI_API_KEY')
YOU_COM_API_KEY = userdata.get('YDC_API_KEY')
LANGCHAIN_API_KEY = userdata.get('LANGCHAIN_API_KEY')

def search_with_you_com(query):
    """
    Use You.com search API to find related articles.
    """
    response = requests.get(
        f"https://api.ydc-index.io/search?query={query}",
        headers={"X-API-Key": f"{YOU_COM_API_KEY}"}
    )
    data = response.json()
    # Assuming a structure of the response, extract URLs or relevant data
    articles = data.get("hits", [])
    return articles

def summarize_and_analyze_content_with_openai(articles):
    """
    Use OpenAI to summarize articles and analyze their content.
    """
    summaries = []
    for article in articles:
        # Here, you would extract the content of the article or pass the URL directly,
        # depending on whether you're able to fetch the article content upfront.
        # For this example, let's assume we're summarizing based on a URL or a snippet.
        prompt = f"Summarize the following article or its content: {article['snippets']}"
        response = requests.post(
            "https://api.openai.com/v1/completions",
            headers={"Authorization": f"Bearer {OPENAI_API_KEY}"},
            json={
                "model": "gpt-3.5-turbo-instruct",
                "prompt": prompt,
                "temperature": 0.5,
                "max_tokens": 150,  # Adjust based on the desired summary length
            },
        )
        data = response.json()
        summary = data.get("choices", [{}])[0].get("text", "").strip()
        summaries.append((summary, article['title'], article['url']))
    return summaries

def evaluate_legitimacy_with_openai(summaries, headline):
    """
    Use OpenAI to evaluate the legitimacy based on summaries.
    """
    combined_summaries = "\n".join(summaries[0])
    combined_titles = "\n".join(summaries[1])
    combined_urls = "\n".join(summaries[2])
    prompt = f"Given these summaries:\n{combined_summaries}, their respective titles: \n{combined_titles} and their respecitive urls: \n{combined_urls}\nHow likely is it that the headline '{headline}' is legitimate? Pay special attention to the dates and try to detect sarcasm in the headlines but just don't say its legitimate based on a fluke. Return the final verdict formed as 'based on the analysis the headline seems legitimate/not legitimate.'"
    response = requests.post(
        "https://api.openai.com/v1/completions",
        headers={"Authorization": f"Bearer {OPENAI_API_KEY}"},
        json={
            "model": "gpt-3.5-turbo-instruct",
            "prompt": prompt,
            "temperature": 0.5,
            "max_tokens": 100,
        },
    )
    data = response.json()
    return data.get("choices", [{}])[0].get("text", "").strip()

def main(headline):
    articles = search_with_you_com(headline)
    summaries = summarize_and_analyze_content_with_openai(articles)
    legitimacy = evaluate_legitimacy_with_openai(summaries, headline)
    print(legitimacy)