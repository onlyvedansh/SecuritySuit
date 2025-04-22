import argparse
import requests

websites = {
    "Facebook": "https://www.facebook.com/{}",
    "YouTube": "https://www.youtube.com/{}",
    "Twitter (X)": "https://www.twitter.com/{}",
    "Instagram": "https://www.instagram.com/{}",
    "LinkedIn": "https://www.linkedin.com/in/{}",
    "Reddit": "https://www.reddit.com/user/{}",
    "Amazon": "https://www.amazon.com/{}",
    "Netflix": "https://www.netflix.com/{}",
    "Coursera": "https://www.coursera.org/user/{}",
    "edX": "https://www.edx.org/user/{}",
    "Airbnb": "https://www.airbnb.com/users/show/{}",
    "Booking.com": "https://www.booking.com/user/{}",
    "Expedia": "https://www.expedia.com/{}",
    "Gmail": "https://mail.google.com/mail/u/0/#search/{}@gmail.com",
    "TripAdvisor": "https://www.tripadvisor.com/members/{}",
    "Google Pay": "https://pay.google.com/payments/u/0/home?username={}",
    "PayPal": "https://www.paypal.me/{}",
    "Uber": "https://www.uber.com/profile/{}",
    "Khan Academy": "https://www.khanacademy.org/profile/{}",
    "Udemy": "https://www.udemy.com/user/{}",
    "Duolingo": "https://www.duolingo.com/profile/{}",
    "Spotify": "https://open.spotify.com/user/{}",
    "GitHub": "https://github.com/{}",
    "Stack Overflow": "https://stackoverflow.com/users/{}",
    "MDN Web Docs": "https://developer.mozilla.org/en-US/profiles/{}",
    "W3Schools": "https://www.w3schools.com/users/{}",
    "CodePen": "https://codepen.io/{}"
}

headers = {
    "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36"
}

def check_username_on_websites(username):
    print(f"\n🔍 Checking username: {username}")
    for site_name, url_template in websites.items():
        try:
            url = url_template.format(username)
            response = requests.get(url, headers=headers, timeout=5)
            if response.status_code == 200:
                print(f"✅ {site_name}: FOUND at {url}")
            else:
                print(f"❌ {site_name}: Not found (Status: {response.status_code})")
        except requests.RequestException as e:
            print(f"⚠️ {site_name}: Could not check (Error: {str(e)})")

def main():
    parser = argparse.ArgumentParser(description="Check if a username exists across multiple websites.")
    parser.add_argument("username", help="The username to check")
    args = parser.parse_args()

    username = args.username.strip().lower()
    if not username:
        print("❗ Error: Username cannot be empty.")
        return

    print("🔐 Username Info Tool")
    check_username_on_websites(username)

if __name__ == "__main__":
    main()