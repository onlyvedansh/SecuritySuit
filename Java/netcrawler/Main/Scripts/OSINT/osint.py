#download: install pip requirements

import random
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
    "User-Agent": "Mozilla/5.0"
}

def get_input(prompt):
    value = input(prompt).strip()
    return "" if value.lower() == "null" else value.lower()

def generate_usernames(first, middle, last, n, extras, base_names):
    usernames = set()

    if not base_names:
        base_names = ["user"]

    if not extras:
        print("⚠️  No DOB/fav number/random selected. Generating usernames using only names.")
        extras = [""]

    while len(usernames) < n:
        base = random.choice(base_names)
        suffix = random.choice(extras)
        username = (base + suffix) if random.choice([True, False]) else (suffix + base)
        usernames.add(username.lower())

    return list(usernames)

def check_username_on_websites(username):
    print(f"\n🔍 Checking username: {username}")
    for site_name, url_template in websites.items():
        try:
            url = url_template.format(username)
            response = requests.get(url, headers=headers, timeout=5)
            if response.status_code == 200:
                print(f"✅ {site_name}: FOUND at {url}")
            else:
                print(f"❌ {site_name}: Not found")
        except requests.RequestException:
            print(f"⚠️ {site_name}: Could not check (connection error)")

def main():
    print("🔐 Username Info Tool")
    choice = input("Do you know the target username? (yes/no): ").strip().lower()

    if choice == 'yes':
        username = input("Enter the username: ").strip()
        check_username_on_websites(username)
    else:
        first = get_input("Enter First Name (or type null): ")
        middle = get_input("Enter Middle Name (or type null): ")
        last = get_input("Enter Last Name (or type null): ")
        dob = get_input("Enter Date of Birth (DD-MM-YYYY or type null): ")
        fav_num = get_input("Enter your favorite number (1–100 or type null): ")

        # Extract date parts if DOB is provided
        day = mob = yob = ""
        if dob:
            parts = dob.split("-")
            if len(parts) == 3:
                day, mob, yob = parts

        # Name-based username bases
        base_names = []
        if first: base_names.append(first)
        if last: base_names.append(last)
        if first and last:
            base_names += [first + last, last + first, first[0] + last, first + last[0]]
        if first and middle and last:
            base_names += [first + middle + last, first[0] + middle[0] + last]

        # Add extras only if values exist and user agrees to include them
        extras = []
        if yob:
            if input("Include Year of Birth in username? (yes/no): ").strip().lower() == "yes":
                extras.append(yob)
        if mob:
            if input("Include Month of Birth in username? (yes/no): ").strip().lower() == "yes":
                extras.append(mob)
        if day:
            if input("Include Day of Birth in username? (yes/no): ").strip().lower() == "yes":
                extras.append(day)
        if fav_num:
            if input("Include Favorite Number in username? (yes/no): ").strip().lower() == "yes":
                extras.append(fav_num)

        include_rand = input("Include Random Number in username? (yes/no): ").strip().lower()
        if include_rand == "yes":
            while True:
                try:
                    digits = int(input("How many digits for the random number? (1–6 recommended): "))
                    if digits < 1 or digits > 10:
                        print("❗ Please choose between 1 and 10 digits.")
                        continue
                    lower = 10**(digits - 1)
                    upper = 10**digits - 1
                    extras.append(str(random.randint(lower, upper)))
                    break
                except ValueError:
                    print("❗ Please enter a valid number.")

        while True:
            try:
                n = int(input("Enter number of usernames to generate: "))
                break
            except ValueError:
                print("❗ Please enter a valid number.")

        usernames = generate_usernames(first, middle, last, n, extras, base_names)

        print("\n🎯 Generated Usernames:")
        for uname in usernames:
            print(f"- {uname}")

        print("\n🌐 Website Checks for Generated Usernames:")
        for uname in usernames:
            check_username_on_websites(uname)

if __name__ == "__main__":
    main()
