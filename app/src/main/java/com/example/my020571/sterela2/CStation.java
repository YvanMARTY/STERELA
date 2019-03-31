package com.example.my020571.sterela2;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Context;
import android.os.CountDownTimer;
import android.widget.Toast;


/**
 * Created by MY020571 on 13/04/2016.
 */



public class CStation {

    // Contexte de cette classe
    private Context context;

    // Bit déterminant la communication
    private byte comm;

    boolean res; //Valeur de retour


    CStation(Context context){
        this.context = context;
    }


    /**
     * Fenêtre permettant de choisir entre un succès ou un échec
     */
    public void connecter() {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);


        // Titre de la fenêtre
        alertDialogBuilder.setTitle("Connexion simulée");
        alertDialogBuilder.setIcon(R.drawable.logo_connecterdeconnecter);
        // Message
        alertDialogBuilder
                .setMessage("Veuillez choisir votre valeur de retour pour votre connexion :\n\n" +
                        "TRUE : Réussite\n" +
                        "FALSE : Echec")
                .setCancelable(false);
                alertDialogBuilder.setPositiveButton("FALSE", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        res = false;
                        Toast.makeText(context, "res = " + res, Toast.LENGTH_LONG).show();
                        type_comm();

                    }
                })
                .setNegativeButton("TRUE", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        res = true;
                        Toast.makeText(context, "res = " + res, Toast.LENGTH_LONG).show();
                        type_comm();
                    }
                });

        // Création
        AlertDialog alertDialog = alertDialogBuilder.create();

        // Affichage
        alertDialog.show();
    }

    /**
     * Fenêtre permettant le choix entre BLUETOOTH et RS232
     */
    public void type_comm(){
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);


        // Titre de la fenêtre
        alertDialogBuilder.setTitle("Connexion simulée");
        alertDialogBuilder.setIcon(R.drawable.logo_connecterdeconnecter);
        // Message
        alertDialogBuilder
                .setMessage("Veuillez choisir votre valeur de retour pour votre connexion :")
                .setCancelable(false);

                alertDialogBuilder.setPositiveButton("RS232", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Octet à 1 pour RS232
                        comm = 1;
                        connecterRS232();
                    }
                })
                .setNegativeButton("BLUETOOTH", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Octet à 0 pour BLUETOOTH
                        comm = 0;
                        connecterBluetooth();
                    }
                });

        // Création

        AlertDialog alertDialog = alertDialogBuilder.create();

        // Affichage
        alertDialog.show();
    }

    /**
     * Méthode permettant de gérer les fenêtres selon le choix de l'utilisateur
     */
    public void connecterBluetooth() {
        // Création du timer
        CountDownTimer timer = new CountDownTimer(2000, 1000) {
            public void onTick(long millisUntilFinished) {
            }
            @Override
            public void onFinish() {
                if (res == false) {
                    // Si l'utilisateur veut un échec et une communication Bluetooth
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                            context);

                    // Titre de la fenêtre
                    alertDialogBuilder.setTitle("Problème de connexion");
                    alertDialogBuilder.setIcon(R.drawable.logo_connecterdeconnecter);

                    // Message
                    alertDialogBuilder
                            .setMessage("La connexion en BLUETOOTH a échouée !")
                            .setCancelable(false)
                            .setIcon(R.drawable.logo_erreur_connexion);

                            alertDialogBuilder.setPositiveButton("ANNULER", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            })
                            .setNegativeButton("RE-ESSAYER", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    if (comm == 1) { // BLUETOOTH
                                        connecterBluetooth();
                                    }
                                } })
                            .setNeutralButton("TYPE", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int id) {
                                    type_comm();
                                }
                            });
                    // Création
                    AlertDialog alertDialog = alertDialogBuilder.create();

                    // Affichage
                    alertDialog.show();

                }
                if(res == true) {
                    // Si l'utilisateur veut un succès et une communication Bluetooth
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                            context);

                    // Titre de la fenêtre
                    alertDialogBuilder.setTitle("Connexion simulée");
                    // Message
                    alertDialogBuilder
                            .setMessage("La connexion en BLUETOOTH a réussi !")
                            .setCancelable(false)
                            .setIcon(R.drawable.logo_connecterdeconnecter)

                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });

                    // Création
                    AlertDialog alertDialog = alertDialogBuilder.create();

                    // Affichage
                    alertDialog.show();
                }
            }
        // Démarrage du timer
        }.start();
    }

    public void connecterRS232() {
        // Création du timer
        CountDownTimer timer = new CountDownTimer(2000, 1000) {
            public void onTick(long millisUntilFinished) {
                // Pour chaque étape, faire ces actions
            }
            @Override
            public void onFinish() {
                if (res == false) {
                    // Si l'utilisateur veut un échec et une communication RS232
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                            context);

                    // Titre de la fenêtre
                    alertDialogBuilder.setTitle("Problème de connexion");
                    alertDialogBuilder.setIcon(R.drawable.logo_connecterdeconnecter);
                    // Message
                    alertDialogBuilder
                            .setMessage("Une erreur de connexion est survenue.")
                            .setCancelable(false)
                            .setIcon(R.drawable.logo_erreur_connexion);

                            alertDialogBuilder.setPositiveButton("ANNULER", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    dialog.cancel();
                                }
                            })
                            .setNegativeButton("RE-ESSAYER", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    if (comm == 1) { // RS232
                                        connecterRS232();
                                    }
                                } })
                            .setNeutralButton("TYPE", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int id) {
                                    type_comm();
                                }
                            });

                    // Création
                    AlertDialog alertDialog = alertDialogBuilder.create();

                    // Affichage
                    alertDialog.show();
                }
                if(res == true) {
                    // Si l'utilisateur veut un succès et une communication RS232
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                                context);

                        // Titre de la fenêtre
                        alertDialogBuilder.setTitle("Connexion simulée");
                        // Message
                        alertDialogBuilder
                                .setMessage("La connexion en RS232 a réussi !")
                                .setCancelable(false)
                                .setIcon(R.drawable.logo_connecterdeconnecter)

                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // if this button is clicked, just close
                                        // the dialog box and do nothing
                                        dialog.cancel();
                                    }
                                });

                        // Création
                        AlertDialog alertDialog = alertDialogBuilder.create();
                        // Affichage
                        alertDialog.show();
                }
            }
        // Démarrage du timer
        }.start();
    }

    public boolean deconnecter() {

        boolean deco = true;

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);

        // Titre de la fenêtre
        alertDialogBuilder.setTitle("Connexion simulée");
        // Message
        alertDialogBuilder
                .setMessage("Voulez-vous simuler une déconnexion ?")
                .setCancelable(false)
                .setIcon(R.drawable.logo_deconnexion)

                .setPositiveButton("OUI", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                })

                .setNegativeButton("NON", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        // Création
        AlertDialog alertDialog = alertDialogBuilder.create();

        // Affichage
        alertDialog.show();

        return deco;
    }

}
