#!/bin/bash
curl    -H "Content-Type: application/json" -X POST --data @insertMap.json --silent http://45.56.121.7:9000/insertMap
echo ""
