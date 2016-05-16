# ![img](https://raw.githubusercontent.com/czak/virgin-balance/master/app/src/main/res/drawable-hdpi/ic_launcher_vb.png) VBalance

VBalance is a simple Android application I built so that I could have a quick
glance overview of my prepaid cellphone account. The [official Virgin Mobile
app](https://play.google.com/store/apps/details?id=pl.virginmobile) is decent,
but this one function was missing.

VBalance presents the account balance as a homescreen widget. Behind the scenes
it uses the same JSON API as the VM website.

## How to use it

![Screen 1](https://raw.githubusercontent.com/czak/vbalance/assets/screenshot.jpg)

1. Choose the VBalance widget
2. Sign in and choose the appropriate phone number
3. Place the widget on your homescreen

## How it works

I anticipated I would need to scrape the `virginmobile.pl` website but it turns
out it uses a JSON API for the account data. After some investigating with cURL,
I found the following endpoints:

* `POST https://virginmobile.pl/spitfire-web-api/api/v1/authentication/login`
  * pass in `username` and `password` in the request
  * a successful sign in response sets a `JSESSIONID` cookie; use it for the
    subsequent requests
* `GET https://virginmobile.pl/spitfire-web-api/api/v1/authentication/logout`
  * pretty self explanatory - kills the session specified by `JSESSIONID` cookie
* `GET https://virginmobile.pl/spitfire-web-api/api/v1/selfCare/msisdnDetails`
  * include the `JSESSIONID` cookie
  * include the header `msisdn: 48123456789`, where `48123456789` is your phone
    number
  * this retrieves a big JSON with all the balance info you could need; the
    challenge now is getting the appropriate fields from the JSON

## Disclaimer

VBalance is in no way affiliated with Virgin Mobile. It's just a
personal project built to solve a personal issue and as such comes with no
guarantees whatsoever.
