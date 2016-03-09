#!/bin/bash
curl    -H "Content-Type: application/json" -X POST --data @insertMap.json --silent http://localhost:9000/insertMap
echo ""
