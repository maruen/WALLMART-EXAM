mysqldump --defaults-extra-file=$WALLMART_EXAM__HOME/conf/dbconf.conf --extended-insert=false --complete-insert --no-create-db --no-create-info wallmart maps_info > ${WALLMART_EXAM_HOME}/conf/evolutions/default/maps_info.sql

