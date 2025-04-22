import requests
import base64
import argparse

# Set up argument parser
parser = argparse.ArgumentParser(description='Check a URL using VirusTotal.')
parser.add_argument('url', help='The URL to scan (e.g., facebook.com, without https://)')
args = parser.parse_args()

# Your VirusTotal API key here
API_KEY = '344a64bf1f9eb9a734e32beaff7596314aa7bfd22dae6fb27108130dd2c997d4'
API_URL = 'https://www.virustotal.com/api/v3/urls'

# Encode the URL to match the required format
def get_encoded_url(url):
    url_bytes = url.encode('utf-8')
    encoded = base64.urlsafe_b64encode(url_bytes).decode().strip("=")
    return encoded

def check_url(url):
    # Prepend https:// to the URL for the API call
    full_url = f"https://{url}"
    headers = {
        "x-apikey": API_KEY,
        "Content-Type": "application/x-www-form-urlencoded"
    }

    print("\n[+] Submitting URL to VirusTotal...")
    response = requests.post(API_URL, headers=headers, data=f"url={full_url}")

    if response.status_code != 200:
        print(f"[-] Error submitting URL: {response.status_code}")
        print(response.json())
        return

    url_id = get_encoded_url(full_url)
    result_url = f"{API_URL}/{url_id}"

    print("[+] Fetching results...")

    result_response = requests.get(result_url, headers=headers)

    if result_response.status_code != 200:
        print(f"[-] Error fetching results: {result_response.status_code}")
        return

    json_data = result_response.json()
    stats = json_data["data"]["attributes"]["last_analysis_stats"]

    print("\n--- VirusTotal URL Analysis ---")
    print(f"URL Scanned  : {full_url}")
    print(f"Harmless     : {stats['harmless']}")
    print(f"Malicious    : {stats['malicious']}")
    print(f"Suspicious   : {stats['suspicious']}")
    print(f"Undetected   : {stats['undetected']}")
    print(f"Timeout      : {stats['timeout']}")
    print("-------------------------------")

# Call the function with the argument URL
check_url(args.url)