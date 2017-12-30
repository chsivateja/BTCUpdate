import json
import time
import requests

from bs4 import BeautifulSoup
from selenium import webdriver
import pyrebase


config = {
    'apiKey': "AIzaSyDYScPduCQ_13Q4rsSTKg9hvYTQ_IEcNDg",
    'authDomain': "btcupdate2017.firebaseapp.com",
    'databaseURL': "https://btcupdate2017.firebaseio.com",
    'projectId': "btcupdate2017",
    'storageBucket': "btcupdate2017.appspot.com",

  }


firebase = pyrebase.initialize_app(config)
db=firebase.database()
def unocoin_update():
    rate = {"buy": "", "sell": "","ref":"","name":""}
    try:

        url = "https://www.unocoin.com/trade?all"


        header = {
            'Host': 'www.unocoin.com',
            'Connection': 'keep-alive',
            'Cache-Control': 'max-age=0',
            'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.84 Safari/537.36',
            'Upgrade-Insecure-Requests': '1',
            'Accept': 'text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8',
            'Accept-Encoding': 'gzip, deflate, br',
            'Accept-Language': 'en-US,en;q=0.9',
            'Cookie': 'Cookie: user_token=c41b1f811c9fd1744e00827e1923184f%2C319f9694-1752-4def-8c75-da8e715e2a01; referrerid=8984; visid_incap_1254633=0tvMaTMRRBWJNLYiRn+6pKzpL1oAAAAAQUIPAAAAAADkGevkIaTxNM2n+GR/KmPl; nlbi_1254633=zw2QURj52X32spyfzr8faQAAAABcsu/mfvHaIvjOWduh5x1K; incap_ses_998_1254633=mDQfZP5H/QqhvYOqxpvZDa3pL1oAAAAAbUwevmHEG5IbZA1nWc/0Bw==; incap_ses_710_1254633=JzWoRck4jD2J3CKEgG3aCa7pL1oAAAAAfqrT3H+8TE2CPS7/72N1YQ==; _ga=GA1.2.1882641985.1513089457; _gid=GA1.2.834144701.1513089457; WZRK_G=a146487736404250ba5c5c2e84aacbb7; screenwidth=1423; _bizo_bzid=5cd01aac-8ea6-44df-b15c-c4524b43a453; _bizo_cksm=4DC91F631600B446; mp_da895dea23e64896c3ee28429a430683_mixpanel=%7B%22distinct_id%22%3A%20%221604b28da9a26d-0fe16138bbda5a-b7a103e-13c680-1604b28daa73bc%22%2C%22%24initial_referrer%22%3A%20%22%24direct%22%2C%22%24initial_referring_domain%22%3A%20%22%24direct%22%7D; WZRK_S_WR5-977-884Z=%7B%22p%22%3A4%2C%22s%22%3A1513089459%2C%22t%22%3A1513089576%7D; _bizo_np_stats=155%3D261%2C; mp_mixpanel__c=3; incap_ses_881_1254633=MDbXXBwYA0p/7AgM6vA5DNPqL1oAAAAA7DSgzCm9Reg/+YZXMAQjiQ=='}
        r = requests.get(url=url, headers=header)
    except Exception as e:
        print(e.message)
    return rate

def localbitcoins_update():
    rate = {"buy": "", "sell": "","ref":"","name":"localbitcoins"}
    try:

        url = "https://localbitcoins.com/"
        r = requests.get(url=url)
        soup = BeautifulSoup(r.text, "html.parser")
        div_buy = soup.find('div', id="purchase-bitcoins-online")
        column_price_buy = div_buy.find('td', {'class': 'column-price'})

        div_sell = soup.find('div', id="sell-bitcoins-online")
        column_price_sell = div_sell.find('td', {'class': 'column-price'})
        print("localbitcoins_update")
        print(column_price_buy.text.strip().replace(",", "").replace(" INR", ""))
        print(column_price_sell.text.strip().replace(",","").replace(" INR",""))
        rate["buy"]=column_price_buy.text.strip().replace(",", "").replace(" INR", "")
        rate["sell"]=column_price_sell.text.strip().replace(",","").replace(" INR","")
    except Exception as e:
        print(e.message)
    return rate

def pocketbits_update():
    rate = {"buy": "", "sell": "","ref":"","name":"pocketbits"}
    print("pocketbits_update")
    try:
        url = 'https://www.pocketbits.in/Index/getBTCRate'

        header = {
            'Host': 'www.pocketbits.in',
            "Connection": 'keep-alive',
            'Content-Length': '0',
            'Accept': '*/*',
            'Origin': 'https://www.pocketbits.in',
            'X-Requested-With': 'XMLHttpRequest',
            'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.84 Safari/537.36',
            'Content-Type': 'application/json; charset=utf-8',
            'Referer': 'https://www.pocketbits.in/',
            "Accept-Encoding": 'gzip, deflate, br',
            'Accept-Language': 'en-US,en;q=0.9',
            'Cookie': '__cfduid=dc854d902d80f06e1d7d706783ca77fd11513092233'
        }
        r = requests.post(url=url, headers=header)
        r = json.loads(r.text)
        btc_buying_rate=r["BTC_BuyingRate"]
        btc_selling_rate=r["BTC_SellingRate"]
        print(btc_buying_rate)
        print(btc_selling_rate)
        rate["buy"]=btc_buying_rate
        rate["sell"]=btc_selling_rate
    except Exception as e:
        print(e.message)
    return rate

def zebpay_update():
    rate = {"buy": "", "sell": "","ref":"","name":"zebpay"}
    print("zebpay_update")
    try:
        url = 'https://live.zebapi.com/api/v1/getrate'
        header = {
            'Content-Type': 'application/x-www-form-urlencodedcharset=utf-8',
            'User-Agent': 'Dalvik/2.1.0 (Linux; U; Android 6.0.1; Redmi 4A MIUI/7.5.25)',
            'Host': 'live.zebapi.com',
            'Connection': 'Keep-Alive',
            'Accept-Encoding': 'gzip',
            'Content-Length': '16'
        }
        data = "currencyCode=INR"

        r = requests.post(url=url, headers=header, data=data)
        r = json.loads(r.text)
        btc_buying_rate = r["buyRate"]
        btc_selling_rate = r["sellRate"]
        print(btc_buying_rate)
        print(btc_selling_rate)
        rate["buy"] = btc_buying_rate
        rate["sell"] = btc_selling_rate
    except Exception as e:
        print(e.message)
    return rate

def throughbit_update():
    rate = {"buy": "", "sell": "","ref":"","name":"throughbit"}
    print("throughbit_update")
    try:
        url = 'https://www.throughbit.com/tbit_ci/index.php/cryptoprice/type/btc/inr'
        header = {
            'Host': 'www.throughbit.com',
            'Connection': 'keep-alive',
            'Accept': 'application/json, text/plain, */*',
            'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.84 Safari/537.36',
            'Referer': 'https://www.throughbit.com/',
            'Accept-Encoding': 'gzip, deflate, br',
            'Accept-Language': 'en-US,en;q=0.9',
            'Cookie': 'visid_incap_1176184=fdQ2xfITQ++HC8BDb3Gbtcz2L1oAAAAAQUIPAAAAAACtiQgZMDcdSAlJqHD3avme; incap_ses_454_1176184=LpWTNfHkYj5rCpLEae9MBsz2L1oAAAAAOllCB7fqunTCJ20RRVBPmQ==; _ga=GA1.2.1097990389.1513092815; _gid=GA1.2.1356368088.1513092815; _gat=1; io=c3YcourevftLKPztGLq7',
        }
        r = requests.get(url=url, headers=header)
        r = json.loads(r.text)
        price = r["data"]['price'][0]
        btc_buying_rate = price["buy_price"]
        btc_selling_rate = price["sell_price"]
        print(btc_buying_rate)
        print(btc_selling_rate)
        rate["buy"] = btc_buying_rate
        rate["sell"] = btc_selling_rate
    except Exception as e:
        print(e.message)
    return rate

def bitlio_update():
    rate = {"buy": "", "sell": "","ref":"","name":"bitlio"}
    print("bitlio_update")
    try:
        url = 'https://www.bitlio.com/api/v1/stats/buy'
        r = requests.get(url=url)
        url1 = 'https://www.bitlio.com/api/v1/stats/sell'
        r1 = requests.get(url=url1)
        price=json.loads(r.text)["inr"]
        btc_buying_rate =price["current_rate"]
        price = json.loads(r1.text)["inr"]
        btc_selling_rate = price["current_rate"]
        print(btc_buying_rate)
        print(btc_selling_rate)
        rate["buy"] = btc_buying_rate
        rate["sell"] = btc_selling_rate
    except Exception as e:
        print(e.message)
    return rate

def buyucoin_update():
    rate = {"buy": "", "sell": "","ref":"","name":"buyucoin"}
    print("buyucoin_update")
    try:
        url = 'https://www.buyucoin.com/altcoin-rate-inr-india'
        r = requests.get(url=url)
        soup = BeautifulSoup(r.text, "html.parser")
        td = soup.find('table', id="inr_rate").findAll('tr')[1].findAll('td')
        btc_buying_rate = td[1].text.strip()
        btc_selling_rate = td[2].text.strip()
        print(btc_buying_rate)
        print(btc_selling_rate)
        rate["buy"] = btc_buying_rate
        rate["sell"] = btc_selling_rate
    except Exception as e:
        print(e.message)
    return rate

def koinex_update():
    rate = {"buy": "", "sell": "","ref":"","name":"koinex"}
    print("koinex_update")
    try:
        url = "https://koinex.in/api/ticker"

        r = requests.get(url=url)
        r=json.loads(r.text)
        btc_buying_rate = r["prices"]["BTC"]
        btc_selling_rate = r["prices"]["BTC"]
        print(btc_buying_rate)
        print(btc_selling_rate)
        rate["buy"] = btc_buying_rate
        rate["sell"] = btc_selling_rate
    except Exception as e:
        print(e.message)
    return rate

c = 1
# while(True):
#     print(c)
#     unocoin_update()
#     c=c+1
#unocoin=unocoin_update()

localbitcoins=localbitcoins_update()
pocketbits=pocketbits_update()
zebpay=zebpay_update()
throughbit=throughbit_update()
bitlio=bitlio_update()
buyucoin=buyucoin_update()
koinex=koinex_update()



data={
    "btc/localbitcoins/": localbitcoins,
    "btc/pocketbits/": pocketbits,
    "btc/zebpay/": zebpay,
    "btc/throughbit/": throughbit,
    "btc/bitlio/": bitlio,
    "btc/buyucoin/": buyucoin,
    "btc/koinex/": koinex

}
try:
    db.update(data)
except Exception as e:
    print(e)

