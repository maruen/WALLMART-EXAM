#!/bin/bash

URL="http://localhost:9000/getRouteWithLessCost?map=$1&origin=$2&destiny=$3&consumeAvg=$4&fuelCost=$5"

curl --request GET "$URL" 

echo ""
