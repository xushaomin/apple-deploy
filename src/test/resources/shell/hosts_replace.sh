#!/bin/sh
#------------------------------------------------------------------------------------------------------
	backup(){
		secs=`date +%s`
		if [ -d /work/shell/hosts/backup ]
		then
			echo "Directory already exists"
		else
			mkdir -p /work/shell/hosts/backup
		fi
		cat /etc/hosts > /work/shell/hosts/backup/hosts_$secs
	}
#------------------------------------------------------------------------------------------------------
	except(){
		if [ $? != 0 ]
		then
        	exit
		fi
	}
#-------------------------------------------------------------------------------------------------------
	BASEDIR=`cd $(dirname $0); pwd`
	cd $BASEDIR
	backup
	wget -O hosts_config.txt 'http://configinfo.appleframework.com/diamond-server/config.co?dataId=etc-host-release&group=host'
	except
	if [ -s hosts_config.txt ]
	then
		cat hosts_config.txt > /etc/hosts
	fi
#-------------------------------------------------------------------------------------------------------