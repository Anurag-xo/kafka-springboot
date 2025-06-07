#!/bin/bash

curl -X POST http://localhost:43555/products \
     -H "Content-Type: application/json" \
     -d '{"title":"iphone","price":800, "quantity":5}'
