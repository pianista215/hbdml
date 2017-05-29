#!/bin/bash
mongoimport --drop --db hotels --collection details --type json --file hotels.json --jsonArray
