BOUCHIKHI NAGIMA PROJET PART 2 

1 ere modif 
![Avant Modif](Picture/PetitModif_NomdeFct.PNG)
Il existe trois méthodes fireAreaInvalidated avec des signatures différentes 
La troisieme méthode prend un FigureEvent en paramètre, mais son nom ne l'indique pas
Modif apportée : Changement du nom de methode par fireAreaInvalidatedEvent, sans oublier de modif ca dans les classe utilisant cette methode une classe ici
Lien commit : ```https://github.com/wumpz/jhotdraw/commit/1292c196386c181d8c0310d348c7c4415be9e0f2 ```



2 eme modif 
![Avant Modif](<Picture/MoyModif_extraction methode validateChangeState.PNG>)
Dans la méthode changed(), il y a une vérification de condition qui pourrait être extraite
``` https://github.com/wumpz/jhotdraw/commit/ac61183b0871106f531d5b611025ead2f7beb860 ```

