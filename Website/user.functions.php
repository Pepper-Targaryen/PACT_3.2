<?php

##### User Functions #####

function changePassword($username,$currentpassword,$newpassword,$newpassword2){
    global $db;
    global $seed;
    if (!valid_username($username) || !user_exists($username))
    {
        return false;
    }
    if (! valid_password($newpassword) || ($newpassword != $newpassword2)){

        return false;
    }

    // we get the current password from the database
    $query = $db->prepare("SELECT password FROM login WHERE username = ? LIMIT 1");
    $query->execute(array($username));
    $row = $query->fetch();

    // compare it with the password the user entered, if they don't match, we return false, he needs to enter the correct password.
    if ($row[0] != sha1($currentpassword.$seed)){

        return false;
    }

    // now we update the password in the database
    $query = $db->prepare("update login set password = ? where username = ?");
    $query->execute(array(sha1($newpassword.$seed),$username));
    return true;
}


function user_exists($username)
{
    global $db;
    if (!valid_username($username))
    {
        return false;
    }

    $query = $db->prepare("SELECT loginid FROM login WHERE username = ? LIMIT 1");
    $query->execute(array($username));
    while($row = $query->fetch()){
        return true;
    }
    return false;

}

function activateUser($uid, $actcode)
{
    global $db;
    $query = $db->prepare("select activated from login where loginid = ? and actcode = ? and activated = 0  limit 1");
    $query->execute(array($uid,$actcode));
    while($row = $query->fetch()){
        $query2 = $db->prepare("update login set activated = '1'  where loginid = ? and actcode = ?");
        $query2->execute(array($uid,$actcode));
        return true;
    }
    return false;

}

function registerNewUser($username, $password, $password2, $email)
{
    global $db;
    global $seed;

    if (!valid_username($username) || !valid_password($password) ||
        !valid_email($email) || $password != $password2 || user_exists($username))
    {
        return false;
    }

    $code = generate_code(20);
    $query = $db->prepare("insert into login (username,password,email,actcode) value (?,?,?,?)");
    $query->execute(array($username,sha1($password . $seed),$email,$code));
    $id = $db->lastInsertId();
    if (sendActivationEmail($username, $password, $id, $email, $code))
    {
        return true;
    } else
    {
        return false;
    }

}

function lostPassword($username, $email)
{
    global $db;
    global $seed;
    if (!valid_username($username) || !user_exists($username) || !valid_email($email))
    {
        return false;
    }

    $query = $db->prepare("select loginid from login where username = ? and email = ? limit 1");
    $query->execute(array($username,$email));

    if(!($row =$query->fetch()) ){
        return false;
    }


    $newpass = generate_code(8);

    $query = $db->prepare("update login set password = ? where username = ?");
    $query->execute(array(sha1($newpass.$seed),$username));


    if (sendLostPasswordEmail($username, $email, $newpass))
    {
        return true;
    } else
    {
        return false;
    }
}

