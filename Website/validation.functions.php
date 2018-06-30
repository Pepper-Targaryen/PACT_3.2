<?php

#### Validation functions ####
function valid_email($email)
{

    // First, we check that there's one @ symbol, and that the lengths are right
    if (!filter_var($email, FILTER_VALIDATE_EMAIL))
    {
        // Email invalid because wrong number of characters in one section, or wrong number of @ symbols.
        echo('forme d\'email non conforme');
        return false;
    }
    return true;
}

function valid_username($username, $minlength = 3, $maxlength = 30)
{

    $username = trim($username);

    if (empty($username))
    {
        echo('pseudo non valide');
        return false; // it was empty
    }
    if (strlen($username) > $maxlength)
    {
        echo('pseudo non valide');
        return false; // too long
    }
    if (strlen($username) < $minlength)
    {
        echo('pseudo non valide');
        return false; //too short
    }

    if (ctype_alnum($username)){
        return true;
    }
    return false;
}

function valid_password($pass, $minlength = 6, $maxlength = 15)
{
    $pass = trim($pass);

    if (empty($pass))
    {
        echo('mot de passe non valide1');
        return false;
    }

    if (strlen($pass) < $minlength)
    {
        echo('mot de passe non valide2');
        return false;
    }

    if (strlen($pass) > $maxlength)
    {
        echo('mot de passe non valide3');
        return false;
    }

    $result = preg_match("/^[A-Za-z0-9_\-]*$/", $pass);

    if ($result)
    {
        return true;
    } else
    {
        echo('mot de passe non valide4');
        return false;
    }

    return false;

}

?>
