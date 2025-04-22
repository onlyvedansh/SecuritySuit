import sys
import time
import hashlib
import requests

API_KEY = '344a64bf1f9eb9a734e32beaff7596314aa7bfd22dae6fb27108130dd2c997d4'  # replace with your actual VirusTotal API key

# Function to get the file's hash (for checking if it's already scanned)
def get_file_hash(filepath):
    with open(filepath, "rb") as f:
        file_hash = hashlib.sha256(f.read()).hexdigest()
    return file_hash

# Upload file to VirusTotal
def upload_file(filepath):
    url = 'https://www.virustotal.com/api/v3/files'
    headers = {
        "x-apikey": API_KEY
    }
    files = {"file": (filepath, open(filepath, "rb"))}
    response = requests.post(url, headers=headers, files=files)
    response.raise_for_status()
    return response.json()["data"]["id"]

# Get report using file ID
def get_report(file_id):
    url = f"https://www.virustotal.com/api/v3/analyses/{file_id}"
    headers = {"x-apikey": API_KEY}
    
    while True:
        response = requests.get(url, headers=headers)
        response.raise_for_status()
        result = response.json()
        
        status = result["data"]["attributes"]["status"]
        if status == "completed":
            return result["data"]["attributes"]["results"]
        
        time.sleep(5)

# Print results
def print_results(results):
    print("\n🧪 VirusTotal Scan Results:")
    for engine, data in results.items():
        category = data["category"]
        result = data["result"]
        print(f"{engine}: {category} ({result})")

# Main
if __name__ == "__main__":
    if len(sys.argv) != 2:
        print("Usage: python virus_check.py <file_path>")
        sys.exit(1)

    filepath = sys.argv[1]

    try:
        print("📤 Uploading file...")
        file_id = upload_file(filepath)

        print("⏳ Waiting for analysis...")
        results = get_report(file_id)

        print_results(results)

    except Exception as e:
        print(f"❌ Error: {e}")
