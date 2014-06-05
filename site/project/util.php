<?php
	//Cassandra has no joins so we make our own.
	function getPersonName($uuidName)
	{
		static $name;
		//print_r($name);
		if(array_key_exists($uuidName, $name)){
			return $name[$uuidName];
		}else{
			global $db_handle;
			$stmt = $db_handle->prepare("SELECT firstname, lastname FROM recycling.users WHERE userid= :uuid");
			$stmt->bindValue(':uuid', $uuidName, PDO::CASSANDRA_UUID);
			$stmt->execute();
			$result = $stmt->fetch(PDO::FETCH_ASSOC);
			$name[$uuidName] = $result['firstname'] . ' ' . $result['lastname'];
			return $name[$uuidName];
		}
	}
	function getLocationName($uuidLocation)
	{
		static $location;
		if(array_key_exists($uuidLocation, $location)){
			return $location[$uuidLocation];
		}else{
			global $db_handle;
			$stmt = $db_handle->prepare("SELECT locationname FROM recycling.location WHERE locationid= :uuid");
			$stmt->bindValue(':uuid', $uuidLocation, PDO::CASSANDRA_UUID);
			$stmt->execute();
			$result = $stmt->fetch(PDO::FETCH_ASSOC);
			$location[$uuidLocation] = $result['locationname'];
			return $location[$uuidLocation];
		}
	}
	function getDateStringFromHex($str)//with thanks to govindarajan on https://github.com/Orange-OpenSource/YACassandraPDO/issues/30
	{
	        $date = unpack('H*', $str);
	        $time = hexdec($date[1]) / 1000;
	        $dateStr = date('d-m-Y H:i:s', $time);
	        return $dateStr;
	}
?>