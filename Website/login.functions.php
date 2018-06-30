<?php

require_once "dbConnect.php";
#### Login Functions #####


function isLoggedIn()
{

    if (isset($_SESSION[('loginid')]) && isset($_SESSION[('username')]))
    {
        return true; // the user is loged in
    } else
    {
        return false; // not logged in
    }

    return false;

}

function checkLogin($u, $p)
{
    global $seed; // global because $seed is declared in the header.php file
    global $db;
    if (!valid_username($u) || !valid_password($p) || !user_exists($u))
    {
        return false; // the name was not valid, or the password, or the username did not exist
    }

    //Now let us look for the user in the database.
    $query = $db-> prepare("SELECT loginid FROM login WHERE username = ? AND password = ? AND disabled = 0 AND activated = 1 LIMIT 1;");
    $query->execute(array($u,sha1($p.$seed)));
    // If the database returns a 0 as result we know the login information is incorrect.
    // If the database returns a 1 as result we know  the login was correct and we proceed.
    // If the database returns a result > 1 there are multiple users
    // with the same username and password, so the login will fail.
    while($row = $query->fetch())
    {
        // Login was successfull
        // Save the user ID for use later
        $_SESSION['loginid'] = $row['loginid'];
        // Save the username for use later
        $_SESSION['username'] = $u;
        // Now we show the userbox
        return true;
    }
    return false;
}

function fetch_build_db($u){
    global $db;
    $query = $db-> prepare("SELECT version, name, id FROM building WHERE owner = ? ;");
    $query->execute(array($u));
    $table = $query->fetchAll();
    return $table;
}

function fetch_one_build_db($u,$id){
    global $db;
    $query = $db-> prepare("SELECT version, name FROM building WHERE owner = ? AND id = ? ;");
    $query->execute(array($u,$id));
    $table = $query->fetch();
    return $table;
}