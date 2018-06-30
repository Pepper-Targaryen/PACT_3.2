<?php

try
{
	$db = new PDO('mysql:host=db729557709.db.1and1.com;dbname=db729557709;charset=utf8', 'dbo729557709', 'KdjkfhgkjoiaL!123');
    $db->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
    $db->setAttribute(PDO::ATTR_EMULATE_PREPARES, false);
}
catch (Exception $e)
{
        die('Erreur : ' . $e->getMessage());
}


