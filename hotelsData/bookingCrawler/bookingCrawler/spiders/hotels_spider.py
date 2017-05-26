import scrapy


class HotelsSpider(scrapy.Spider):
    name = "hotels"

    start_urls = [
        "https://www.booking.com/searchresults.en-gb.html?city=-390625&checkin_monthday=17&checkin_month=11&checkin_year=2017&checkout_monthday=18&checkout_month=11&checkout_year=2017&room1=A,&no_rooms=1&group_adults=2&group_children=0&order=price",
        "https://www.booking.com/searchresults.en-gb.html?city=20088325&checkin_monthday=17&checkin_month=11&checkin_year=2017&checkout_monthday=18&checkout_month=11&checkout_year=2017&room1=A,&no_rooms=1&group_adults=2&group_children=0&order=price",
        "https://www.booking.com/searchresults.en-gb.html?city=-2601889&checkin_monthday=17&checkin_month=11&checkin_year=2017&checkout_monthday=18&checkout_month=11&checkout_year=2017&room1=A,&no_rooms=1&group_adults=2&group_children=0&order=price",
        "https://www.booking.com/searchresults.en-gb.html?city=-3414440&checkin_monthday=17&checkin_month=11&checkin_year=2017&checkout_monthday=18&checkout_month=11&checkout_year=2017&room1=A,&no_rooms=1&group_adults=2&group_children=0&order=price",
        "https://www.booking.com/searchresults.en-gb.html?city=-1603135&checkin_monthday=17&checkin_month=11&checkin_year=2017&checkout_monthday=18&checkout_month=11&checkout_year=2017&room1=A,&no_rooms=1&group_adults=2&group_children=0&order=price",
        "https://www.booking.com/searchresults.en-gb.html?city=-1898541&checkin_monthday=17&checkin_month=11&checkin_year=2017&checkout_monthday=18&checkout_month=11&checkout_year=2017&room1=A,&no_rooms=1&group_adults=2&group_children=0&order=price",
        "https://www.booking.com/searchresults.en-gb.html?city=-246227&checkin_monthday=17&checkin_month=11&checkin_year=2017&checkout_monthday=18&checkout_month=11&checkout_year=2017&room1=A,&no_rooms=1&group_adults=2&group_children=0&order=price",
        "https://www.booking.com/searchresults.en-gb.html?city=-345275&checkin_monthday=17&checkin_month=11&checkin_year=2017&checkout_monthday=18&checkout_month=11&checkout_year=2017&room1=A,&no_rooms=1&group_adults=2&group_children=0&order=price"
    ]

    def parse(self, response):
        city = response.css("#ss::attr(value)").extract_first()
        self.log('City: %s' % city)
        for hotel in response.css("[data-hotelid]"):
            name = hotel.css(".sr-hotel__name::text").extract_first().strip()
            stars = hotel.css("::attr(data-class)").extract_first().strip()
            price = hotel.css('strong').css("b::text").extract_first()
            if price is not None:
                #Avoid € storage
                price_value = price.strip()[2:]
                yield {
                    'city': city,
                    'name': name,
                    'stars': stars,
                    'price': price_value
                }

        next_page = response.css('[data-page-next]::attr(href)').extract_first()
        if next_page is not None:
            next_page = response.urljoin(next_page)
            yield scrapy.Request(next_page, callback=self.parse)