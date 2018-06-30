<?php

require_once("mail.functions.php");
require_once("user.functions.php");
require_once("display.functions.php");
require_once("login.functions.php");
require_once("validation.functions.php");



function generate_code($length = 10)
{

    if ($length <= 0)
    {
        return false;
    }

    $code = "";
    $chars = "abcdefghijklmnpqrstuvwxyzABCDEFGHIJKLMNPQRSTUVWXYZ123456789";
    srand((double)microtime() * 1000000);
    for ($i = 0; $i < $length; $i++)
    {
        $code = $code . substr($chars, rand() % strlen($chars), 1);
    }
    return $code;

}

?>
