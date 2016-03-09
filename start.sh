#!/bin/bash

if [  "$1" == "prod"  ]; then
	echo "[info] Backing up database files..."
  	${WALLMART_HOME}/scripts/dumpAll.sh
	
	echo "[info] Starting server in production mode"
	JAVA_OPTS="-Dhttps.port=443
		  		  -Dhttp.port=80
		        -DapplyEvolutions.default=true
		  		  -DapplyDownEvolutions.default=true" activator start
else
		echo "[info] Starting server in development mode"
		JAVA_OPTS="-Dhttps.port=9443
		        -Dhttp.port=9000
		        -Dhttps.keyStore=conf/generated.keystore"  activator -jvm-debug 9999 run

fi
