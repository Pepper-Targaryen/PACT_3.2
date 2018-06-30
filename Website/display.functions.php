<?php

#### Display Functions ####

function show_userbox()
{
    // retrieve the session information
    $u = $_SESSION['username'];
    $uid = $_SESSION['loginid'];
    // display the user box
    echo('<div class="row">
  <div class="col-sm-2">
    <legend> Bonjour '. $u .'<legend> </div>
  <div class="col-sm-5"></div>
  <div class="col-sm-3">
  <a class="btn btn-secondary" href=\'./changepassword.php\'>Changer le mot de passe</a>
</div>
  
  <div class="col-sm-2">
  <a class="btn btn-danger" href=\'./logout.php\'>Déconnexion</a>
</div>
</div>
');

}

function show_changepassword_form(){

    echo '<form action="./changepassword.php" method="post"> 
  <fieldset> 
  <legend>Changer le mot de passe</legend> 
  <input type="hidden" value="'.$_SESSION['username'].'" name="username"> 
  <dl> 
    <dt> 
      <label for="oldpassword">Mot de passe actuel:</label> 
    </dt> 
    <dd> 
      <input name="oldpassword" type="password" id="oldpassword" maxlength="15"> 
    </dd> 
  </dl> 
  <dl> 
    <dt> 
      <label for="password">Nouveau mot de passe:</label> 
    </dt> 
    <dd> 
      <input name="password" type="password" id="password" maxlength="15"> 
    </dd> 
  </dl> 
  <dl> 
    <dt> 
      <label for="password2">Re-tapez le nouveau mot de passe:</label> 
    </dt> 
    <dd> 
      <input name="password2" type="password" id="password2" maxlength="15"> 
    </dd> 
  </dl> 
  <p> 
    <input class="btn btn-primary" name="change" type="submit" value="Changer"> 
    <input class="btn btn-warning" name="reset" type="reset" value="Réinitialiser">
    <a href="index.php" class="btn btn-secondary" role="button">Retour</a>
  </p> 
  </fieldset> 
</form>
';
}

function show_loginform($disabled = false)
{

    echo '<form name="login-form" id="login-form" method="post" action="./index.php"> 
  <fieldset> 
  <legend>Connexion</legend> 
  <dl> 
    <dt><label title="Username">Pseudo: </label></dt> 
    <dd><input tabindex="1" autofocus accesskey="u" name="username" type="text" maxlength="30" id="username" /></dd> 
  </dl> 
  <dl> 
    <dt><label title="Password">Mot de passe: </label></dt> 
    <dd><input tabindex="2" accesskey="p" name="password" type="password" maxlength="15" id="password" /></dd> 
  </dl> 
  <ul> 
    <li><a href="./register.php" title="Register">Inscription</a></li> 
    <li><a href="./lostpassword.php" title="Lost Password">Mot de passe oublié?</a></li> 
  </ul> 
  <p><input class="btn btn-success" tabindex="3" accesskey="l" type="submit" name="cmdlogin" value="Login" ';
    if ($disabled == true)
    {
        echo 'disabled="disabled"';
    }
    echo ' /></p></fieldset></form>';


}

function show_lostpassword_form(){

    echo '<form action="./lostpassword.php" method="post"> 
	<fieldset><legend>Reset Password</legend>
  <dl> 
    <dt><label for="username">Username:</label></dt> 
    <dd><input name="username" type="text" id="username" maxlength="30">
    </dd> 
  </dl> 
   <dl> 
    <dt><label for="email">email:</label></dt> 
    <dd><input name="email" type="text" id="email" maxlength="255">
    </dd> 
  </dl> 
  <p> 
    <input name="reset" type="reset" value="Reset"> 
    <input name="lostpass" type="submit" value="Reset Password"> 
  </p> 
  </fieldset>
</form>';

}

function show_registration_form(){

    echo '<form action="./register.php" method="post"> 
	<fieldset><legend>Register</legend>
  <dl> 
    <dt><label for="username">Username:</label></dt> 
    <dd><input name="username" type="text" id="username" maxlength="30">
    </dd> 
  </dl> 
  <dl> 
    <dt><label for="password">Password:</label></dt> 
    <dd><input name="password" type="password" id="password" maxlength="15">
    </dd> 
  </dl> 
  <dl> 
    <dt><label for="password2">Re-type password:</label></dt> 
    <dd><input name="password2" type="password" id="password2" maxlength="15">
    </dd> 
  </dl> 
  <dl> 
    <dt><label for="email">email:</label></dt> 
    <dd><input name="email" type="text" id="email" maxlength="255">
    </dd> 
  </dl> 
  <p> 
    <input name="reset" type="reset" value="Reset"> 
    <input name="register" type="submit" value="Register"> 
  </p> 
  </fieldset>
</form>';

}

function show_buildings(){
    $table = fetch_build_db($_SESSION['username']);
    echo ('<table class="table table-hover">
    <thead>
      <tr>
        <th>Nom du bâtiment</th>
        <th>Version</th>
        <th></th>
      </tr>
    </thead>
    <tbody>');
    foreach ($table as $row){
        echo('
        <tr>
        <td>'. $row["name"] .'</td>
        <td>'. $row["version"] .'</td>
        <td><a href="building.php?action=modify&id='. $row["id"]. '" class="btn btn-primary" role="button">Modifier</a>
</td>
      </tr>');
    }
    echo("
    </tbody>
    </table>");
    echo('<a href="building.php?action=add" class="btn btn-info" role="button">Ajouter un bâtiment</a>
');

}

function show_modify_form($id){
    global $db;
    $buildData = fetch_one_build_db($_SESSION["username"],$id);
    if (empty($buildData)){
        echo('<div class="alert alert-danger">Vous n\'avez pas le droit de modifier ce bâtiment!</div>');
    }
    else{
        echo('
<form action="building.php" enctype="multipart/form-data" method="post">
    <div class="form-group">
        <label for="name">Nom du bâtiment: </label>
        <input name="name" type="text" class="form-control" id="name" value='.$buildData["name"].'>
    </div>
    <div class="form-group">
        <label for="version">Version: </label>
        <input name="version" type="number" class="form-control" id="version" value='.$buildData["version"].'>
    </div>
    <div class="form-group">
        Sélectionner un fichier .tiff:
        <input type="file" name="fileToUpload" id="fileToUpload">
    </div>
    <div class="form-group">
        <label for="name">id du bâtiment: </label>
        <input name="id" type="text" class="form-control" id="id" readonly value='.$id.'>
    </div>
    <button type="submit" class="btn btn-primary">Sauvegarder</button>
    <a href="index.php" class="btn btn-secondary" role="button">Retour</a>
</form>
');
    }
}
function show_add_form(){
    global $db;
    echo('
<form action="building.php" enctype="multipart/form-data" method="post">
    <div class="form-group">
        <label for="name">Nom du bâtiment: </label>
        <input name="name" type="text" class="form-control" id="name">
    </div>
    <div class="form-group">
        Sélectionner un fichier .tiff:
        <input type="file" name="fileToUpload" id="fileToUpload">
    </div>
    <button type="submit" class="btn btn-primary">Ajouter</button>
    <a href="index.php" class="btn btn-secondary" role="button">Retour</a>
</form>
');
}
