package com.example.my020571.sterela2;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;


public class MainActivity extends AppCompatActivity {


    private Button MENU;
    private static Context context_z;
    final Context context = this;
    private static final int TEXT_ID = 0;

    public CStation station = new CStation(context);

    String XMLFile;

    public String vitesse_;
    public String bitDeDonnees_;
    public String bitParite_;
    public String bitArret_;
    public String bitControleDeFlux_;


    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context_z = this;
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // ECRAN TOUJOURS ALLUME
        // Window flag: as long as this window is visible to the user, keep the device's screen turned on and bright.
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        // DESACTIVE LE VEROUILLAGE (MODE DE DEVEROUILLAGE), MAIS LE VEROUILLAGE RESTE POSSIBLE SI ON APPUIE SUR LE BOUTON POWER
        // Window flag: when set the window will cause the keyguard to be dismissed, only if it is not a secure lock keyguard.
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);

        // LANCE L'APPLICATION EN MODE PAYSAGE
        // Change the desired orientation of this activity.
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);


        setContentView(R.layout.content_main);

        timer();

        MENU = (Button) findViewById(R.id.BouttonMenu);

        MENU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                // Creation des instances du PopUpMenu
                PopupMenu popup = new PopupMenu(MainActivity.this, MENU);
                //Inflating le PopUpMenu en utilisant le fichier XML
                popup.getMenuInflater().inflate(R.menu.menu_main, popup.getMenu());

                // Enregistre PopUpMenu avec OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.Langue:
                                MainActivity.this.changerLangue();
                                return true;
                            case R.id.Affichage:
                                affichage();
                                return true;
                            case R.id.ConnecterDeconnecter:
                                seConnecter();
                                return true;
                            case R.id.Luminosité:
                                luminosite();
                                return true;
                            case R.id.Mode:
                                changerMode();
                                return true;
                            case R.id.Quitter:
                                mdp();
                                return true;
                            default:
                                return false;
                        }
                    }
                });

                popup.show(); //Affichage du menu
            }
        });

    }



    /**
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }


    /**
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);

    }

    /**
     * Fenêtre pour la déconnexion de l'application à la station météo
     */
    public void seDeconnecter() {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);


        // Titre de la fenêtre
        alertDialogBuilder.setTitle("Se déconnecter");
        // set dialog message
        alertDialogBuilder
                .setMessage("Déconnexion OK")
                .setCancelable(false)
                .setIcon(R.drawable.logo_deconnexion)


                .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });


        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }


    /**
     * Fenêtre permettant de change la langue de l'application
     */
    public void changerLangue() {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);

        // Récupération du fichier XML
        final View view = View.inflate(MainActivity.this, R.layout.changer_langue, null);
        // Parametrage de la vue
        alertDialogBuilder.setView(view);

        // Titre de la fenêtre
        alertDialogBuilder.setTitle("Langue");
        // set dialog message
        alertDialogBuilder
                .setMessage("Veuillez choisir votre langue :")
                .setCancelable(false)
                .setIcon(R.drawable.logo_langue)

                .setPositiveButton("ANNULER", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                })
                .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        RadioButton f; // Récupération du bouton radio " Français "
                        f = (RadioButton) view.findViewById(R.id.BouttonRADIO_FR);

                        RadioButton e; // Récupération du bouton radio " Anglais "
                        e = (RadioButton) view.findViewById(R.id.BouttonRADIO_EN);

                        if (e.isChecked()) {
                            Locale locale = new Locale("en");
                            Locale.setDefault(locale);
                            Configuration config = new Configuration();
                            config.locale = locale;
                            getBaseContext().getResources().updateConfiguration(config,
                                    getBaseContext().getResources().getDisplayMetrics());
                        }
                        if (f.isChecked()) {
                            Locale locale = new Locale("fr");
                            Locale.setDefault(locale);
                            Configuration config = new Configuration();
                            config.locale = locale;
                            getBaseContext().getResources().updateConfiguration(config,
                                    getBaseContext().getResources().getDisplayMetrics());
                        }
                    }
                });

        // Création
        AlertDialog alertDialog = alertDialogBuilder.create();

        // Affichage à l'écran
        alertDialog.show();
    }


    /**
     * Fenêtre permettant de changer le mode (JOUR / NUIT) de l'application
     */
    public void changerMode() {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);
        // Récupération du fichier XML
        final View view = View.inflate(MainActivity.this, R.layout.changer_mode, null);
        // Parametrage de la vue
        alertDialogBuilder.setView(view);

        // Titre de la fenêtre
        alertDialogBuilder.setTitle("Mode");
        // Message
        alertDialogBuilder
                .setMessage("Veuillez choisir votre mode :")
                .setCancelable(false)
                .setIcon(R.drawable.logo_mode)
                .setPositiveButton("ANNULER", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        }

                )
                .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // if this button is clicked, just close
                                // the dialog box and do nothing
                                RadioButton j; // Récupération du bouton radio " JUIT "
                                j = (RadioButton) view.findViewById(R.id.BouttonRADIO_JOUR);
                                RadioButton n; // Récupération du bouton radio " NUIT "
                                n = (RadioButton) view.findViewById(R.id.BouttonRADIO_NUIT);

                                if (j.isChecked()) {
                                    TextView bloc1;
                                    bloc1 = (TextView) findViewById(R.id.Bloc_1);
                                    bloc1.setTextColor(Color.BLACK);
                                    bloc1.setBackgroundColor(Color.WHITE);

                                    TextView bloc2;
                                    bloc2 = (TextView) findViewById(R.id.Bloc_2);
                                    bloc2.setTextColor(Color.BLACK);
                                    bloc2.setBackgroundColor(Color.WHITE);

                                    TextView bloc3;
                                    bloc3 = (TextView) findViewById(R.id.Bloc_3);
                                    bloc3.setTextColor(Color.BLACK);
                                    bloc3.setBackgroundColor(Color.WHITE);

                                    TextView bloc4;
                                    bloc4 = (TextView) findViewById(R.id.Bloc_4);
                                    bloc4.setTextColor(Color.BLACK);
                                    bloc4.setBackgroundColor(Color.WHITE);

                                    TextView bloc5;
                                    bloc5 = (TextView) findViewById(R.id.Bloc_5);
                                    bloc5.setTextColor(Color.BLACK);
                                    bloc5.setBackgroundColor(Color.WHITE);

                                    TextView bloc6;
                                    bloc6 = (TextView) findViewById(R.id.Bloc_6);
                                    bloc6.setTextColor(Color.BLACK);
                                    bloc6.setBackgroundColor(Color.WHITE);

                                    TextView bloc7;
                                    bloc7 = (TextView) findViewById(R.id.Bloc_7);
                                    bloc7.setTextColor(Color.BLACK);
                                    bloc7.setBackgroundColor(Color.WHITE);

                                    TextView bloc8;
                                    bloc8 = (TextView) findViewById(R.id.Bloc_8);
                                    bloc8.setTextColor(Color.BLACK);
                                    bloc8.setBackgroundColor(Color.WHITE);

                                    TextView clock;
                                    clock = (TextClock) findViewById(R.id.Heure);
                                    clock.setTextColor(Color.BLACK);
                                }

                                if (n.isChecked()) {
                                    TextView bloc1;
                                    bloc1 = (TextView) findViewById(R.id.Bloc_1);
                                    bloc1.setTextColor(Color.WHITE);
                                    bloc1.setBackgroundColor(Color.BLACK);

                                    TextView bloc2;
                                    bloc2 = (TextView) findViewById(R.id.Bloc_2);
                                    bloc2.setTextColor(Color.WHITE);
                                    bloc2.setBackgroundColor(Color.BLACK);

                                    TextView bloc3;
                                    bloc3 = (TextView) findViewById(R.id.Bloc_3);
                                    bloc3.setTextColor(Color.WHITE);
                                    bloc3.setBackgroundColor(Color.BLACK);

                                    TextView bloc4;
                                    bloc4 = (TextView) findViewById(R.id.Bloc_4);
                                    bloc4.setTextColor(Color.WHITE);
                                    bloc4.setBackgroundColor(Color.BLACK);

                                    TextView bloc5;
                                    bloc5 = (TextView) findViewById(R.id.Bloc_5);
                                    bloc5.setTextColor(Color.WHITE);
                                    bloc5.setBackgroundColor(Color.BLACK);

                                    TextView bloc6;
                                    bloc6 = (TextView) findViewById(R.id.Bloc_6);
                                    bloc6.setTextColor(Color.WHITE);
                                    bloc6.setBackgroundColor(Color.BLACK);

                                    TextView bloc7;
                                    bloc7 = (TextView) findViewById(R.id.Bloc_7);
                                    bloc7.setTextColor(Color.WHITE);
                                    bloc7.setBackgroundColor(Color.BLACK);

                                    TextView bloc8;
                                    bloc8 = (TextView) findViewById(R.id.Bloc_8);
                                    bloc8.setTextColor(Color.WHITE);
                                    bloc8.setBackgroundColor(Color.BLACK);

                                    TextView clock;
                                    clock = (TextClock) findViewById(R.id.Heure);
                                    clock.setTextColor(Color.BLACK);
                                }
                            }
                        }

                );


        // Création
        AlertDialog alertDialog = alertDialogBuilder.create();

        // Affichage à l'écran
        alertDialog.show();
    }


    /**
     * Fenêtre permettant de fermer l'application, avec le mode de passe
     */
    public void mdp() {
        // Definition du MDP en BRUT
        final String PASSWORD = "STERELA";


        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);

        // Titre de la fenêtre
        alertDialogBuilder.setTitle("Quitter");
        // Message
        alertDialogBuilder.setMessage("Veuillez saisir le mot de passe pour quitter l'application :");
        alertDialogBuilder.setCancelable(false)
                .setIcon(R.drawable.logo_password);


        // Création du champ de saisie pour le MDP
        final EditText champDeSaisieMDP = new EditText(this);
        // Definition d'un ID pour le champ de saisie
        champDeSaisieMDP.setId(TEXT_ID);
        // Parametrage de la vue
        alertDialogBuilder.setView(champDeSaisieMDP);
        // Texte saisie : Texte de type PASSWORD -> Saisie cachée
        champDeSaisieMDP.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);


        alertDialogBuilder.setPositiveButton("ANNULER", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                // Fermeture du clavier
                getWindow().setSoftInputMode(
                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
                );
                // Fermeture de la boîte de dialogue
                dialog.cancel();
            }

        });

        alertDialogBuilder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                String MDP = "";
                // Récupération du texte saisie, puis convertie en string
                MDP = champDeSaisieMDP.getText().toString();

                // Verification du mot de passe
                if (MDP.equals(PASSWORD)) {
                    // Fermeture de l'application
                    finish();
                } else {
                    // Message d'erreur
                    Toast.makeText(context, "Mot de passe incorrect.", Toast.LENGTH_LONG).show();
                    mdp();
                }
            }
        });

        // Creation
        AlertDialog alertDialog = alertDialogBuilder.create();

        // Affichage
        alertDialog.show();
    }

    /**
     * Fenêtre permettant de choisir le nombre de blocs
     */
    public void affichage() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);
        // Définition du contexte
        Context mContext = getApplicationContext();
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
        final View layout = inflater.inflate(R.layout.affichage_nbre_blocs, null);

        // Création du menu déroulant
        final String array_spinner[];
        array_spinner = new String[5];

        // Définition du texte des items du menu déroulant
        array_spinner[0] = "4BlocsXML.xml";
        array_spinner[1] = "5BlocsXML.xml";
        array_spinner[2] = "6BlocsXML.xml";
        array_spinner[3] = "7BlocsXML.xml";
        array_spinner[4] = "8BlocsXML.xml";

        // Récupération du fichier XML
        final Spinner nbre_blocs = (Spinner) layout.findViewById(R.id.spinner_nbre_Blocs);
        // Affichage sous forme d'un menu déroulant
        ArrayAdapter adapter_nbre_blocs = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, array_spinner);

        nbre_blocs.setAdapter(adapter_nbre_blocs);
        // Parametrage de la vue
        alertDialogBuilder.setView(layout);

        // Titre de la fenêtre
        alertDialogBuilder.setTitle("Affichage");
        // Texte du contenu de la fenêtre
        alertDialogBuilder
                .setMessage("Veuillez choisir un thème d'affichage : \n")
                .setCancelable(false)
                .setIcon(R.drawable.logo_affichage)
                .setPositiveButton("ANNULER", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();
                    }
                })
                .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Récupération de l'tiem chosie
                        Object objet = nbre_blocs.getSelectedItem();
                        if (objet instanceof String) {
                            XMLFile = (String) objet;
                            XMLFile = "/mnt/sdcard/meteo/" + XMLFile;
                        }
                        // Affichage pour vérifier le chemin du fichier XML choisi
                        Toast.makeText(context,"XML : "+XMLFile, Toast.LENGTH_LONG).show();
                    }
                });


        // Création
        AlertDialog alertDialog = alertDialogBuilder.create();

        // Affichage
        alertDialog.show();
    }


    /**
     * Fenêtre permettant de connecter l'application à la station météo, avec les deux types de connexion
     * Ici, je simule une connexion depuis l'applciation vers la station météo, prêtée par STERELA
     */
    public void seConnecter() {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);

        // Titre de la fenêtre
        alertDialogBuilder.setTitle("Connexion simulée");
        alertDialogBuilder.setIcon(R.drawable.logo_connecterdeconnecter);
        // Message
        alertDialogBuilder
                .setMessage("Voulez vous simuler une connexion :")
                .setCancelable(false);

                alertDialogBuilder.setPositiveButton("NON", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                })
                .setNegativeButton("OUI", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        station.connecter();
                    }
                });


        // Création
        final AlertDialog alertDialog = alertDialogBuilder.create();


        // Affichage
        alertDialog.show();
    }


    /**
     * Fenêtre permettant de signaler un problème de connexion entre l'application et la station météo
     */
    public void pb_connexion() {

        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context.getApplicationContext());


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
                        dialog.cancel();
                    }
                })
                .setNeutralButton("TYPE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        station.connecter();
                    }
                });


        // Création
        final AlertDialog alertDialog = alertDialogBuilder.create();


        // Affichage
        alertDialog.show();

    }


    /**
     * Fenêtre permettant de configurer la connexion RS232
     */
    public void parametres_rs232() {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);
        // Parametrage du contexte
        Context mContext = getApplicationContext();
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
        final View layout = inflater.inflate(R.layout.parametres_rs232, null);

        // Création du texte des items du menu déroulant
        final String array_spinner[];
        array_spinner = new String[18];

        array_spinner[0] = "75";
        array_spinner[1] = "110";
        array_spinner[2] = "134";
        array_spinner[3] = "150";
        array_spinner[4] = "300";
        array_spinner[5] = "600";
        array_spinner[6] = "1 200";
        array_spinner[7] = "1 800";
        array_spinner[8] = "2 400";
        array_spinner[9] = "4 800";
        array_spinner[10] = "7 200";
        array_spinner[11] = "9 600";
        array_spinner[12] = "14 400";
        array_spinner[13] = "19 200";
        array_spinner[14] = "38 400";
        array_spinner[15] = "57 600";
        array_spinner[16] = "115 200";
        array_spinner[17] = "128 000";


        final Spinner vitesse_rs232 = (Spinner) layout.findViewById(R.id.spinner_vitesse);

        ArrayAdapter adapter_vitesse_rs232 = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, array_spinner);

        vitesse_rs232.setAdapter(adapter_vitesse_rs232);
        vitesse_rs232.setSelection(16);


        // Création du texte des items du menu déroulant
        final String array_spinner_2[];
        array_spinner_2 = new String[5];

        array_spinner_2[0]="4";
        array_spinner_2[1]="5";
        array_spinner_2[2]="6";
        array_spinner_2[3]="7";
        array_spinner_2[4]="8";


        final Spinner bitDeDonnees = (Spinner) layout.findViewById(R.id.spinner_bitDeDonnees);

        ArrayAdapter adapter_bitDeDonnees = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, array_spinner_2);

        bitDeDonnees.setAdapter(adapter_bitDeDonnees);
        bitDeDonnees.setSelection(4);


        // Création du texte des items du menu déroulant
        final String array_spinner_3[];
        array_spinner_3 = new String[5];

        array_spinner_3[0]="Paire";
        array_spinner_3[1]="Impaire";
        array_spinner_3[2]="Aucune";
        array_spinner_3[3]="Marque";
        array_spinner_3[4]="Espace";


        final Spinner bitParite = (Spinner) layout.findViewById(R.id.spinner_bitParite);

        ArrayAdapter adapter_bitParite = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, array_spinner_3);

        bitParite.setAdapter(adapter_bitParite);
        bitParite.setSelection(2);

        // Création du texte des items du menu déroulant
        final String array_spinner_4[];
        array_spinner_4 = new String[3];

        array_spinner_4[0]="1";
        array_spinner_4[1]="1.5";
        array_spinner_4[2]="2";


        final Spinner bitArret = (Spinner) layout.findViewById(R.id.spinner_bitArret);

        ArrayAdapter adapter_bitArret = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, array_spinner_4);

        bitArret.setAdapter(adapter_bitArret);
        bitArret.setSelection(0);


        // Création du texte des items du menu déroulant
        final String array_spinner_5[];
        array_spinner_5 = new String[3];

        array_spinner_5[0]="Xon / Xoff";
        array_spinner_5[1]="Matériel";
        array_spinner_5[2]="Aucun";

        final Spinner bitControleDeFlux = (Spinner) layout.findViewById(R.id.spinner_bitControleDeFlux);

        ArrayAdapter adapter_bitControleDeFlux = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, array_spinner_5);

        bitControleDeFlux.setAdapter(adapter_bitControleDeFlux);
        bitControleDeFlux.setSelection(1);

        alertDialogBuilder.setView(layout);

        // Titre de la fenêtre
        alertDialogBuilder.setTitle("Paramètres connexion RS232");
        // Message
        alertDialogBuilder
                .setMessage("Veuillez choisir vos paramètres pour la connexion RS232 :")
                .setCancelable(false)
                .setIcon(R.drawable.logo_parametres_rs232)
                .setPositiveButton("ANNULER", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        seConnecter();
                    }
                })
                .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Object objet_v = vitesse_rs232.getSelectedItem();
                        if (objet_v instanceof String) {
                            vitesse_ = (String) objet_v;
                        }
                        vitesse_.toString();
                        Object objet_d = bitDeDonnees.getSelectedItem();
                        if (objet_d instanceof String) {
                            bitDeDonnees_ = (String) objet_d;
                        }
                        bitDeDonnees_.toString();
                        Object objet_p = bitParite.getSelectedItem();
                        if (objet_p instanceof String) {
                            bitParite_ = (String) objet_p;
                        }
                        bitParite_.toString();
                        Object objet_a = bitArret.getSelectedItem();
                        if (objet_a instanceof String) {
                            bitArret_ = (String) objet_a;
                        }
                        bitArret_.toString();
                        Object objet_f = bitControleDeFlux.getSelectedItem();
                        if (objet_f instanceof String) {
                            bitControleDeFlux_ = (String) objet_f;
                        }
                        bitControleDeFlux_.toString();
                        seConnecter();
                    }});

        // Création
        final AlertDialog alertDialog = alertDialogBuilder.create();

        // Affichage
        alertDialog.show();
    }


    /**
     * Fenêtre permettant de configurer la luminosité
     */
    public void luminosite() {

        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);
        // Récupération du fichier XML
        final View view = View.inflate(MainActivity.this, R.layout.barre_de_luminosite, null);

        // Titre de la fenêtre
        alertDialogBuilder.setTitle("Luminosité");
        // Message
        alertDialogBuilder
                .setMessage("Veuillez ajuster votre luminosité :")
                .setIcon(R.drawable.logo_luminosite)
                .setCancelable(false);
        // Parametrage de la vue
        alertDialogBuilder.setView(view);

                alertDialogBuilder.setPositiveButton("ANNULER", new DialogInterface.OnClickListener()

                        {
                            public void onClick(DialogInterface dialog, int id) {
                                // if this button is clicked, close
                                // current activity
                                dialog.cancel();
                            }
                        }

                )
                .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // if this button is clicked, just close
                                // the dialog box and do nothing
                                SeekBar brightness;
                                brightness = (SeekBar) view.findViewById(R.id.seekBar_luminosite);

                                brightness.setMax(255);
                                float curBrightnessValue = 0;

                                try {
                                    curBrightnessValue = android.provider.Settings.System.getInt(
                                            getContentResolver(),
                                            android.provider.Settings.System.SCREEN_BRIGHTNESS);
                                } catch (Settings.SettingNotFoundException e) {
                                    e.printStackTrace();
                                }
                                int screen_brightness = (int) curBrightnessValue;
                                brightness.setProgress(screen_brightness);

                                brightness.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                                    int progress = 0;


                                    @Override
                                    public void onProgressChanged(SeekBar seekBar, int progresValue,
                                                                  boolean fromUser) {
                                        progress = progresValue;
                                    }

                                    @Override
                                    public void onStartTrackingTouch(SeekBar seekBar) {
                                        // Do something here,
                                        // if you want to do anything at the start of
                                        // touching the seekbar
                                    }

                                    @Override
                                    public void onStopTrackingTouch(SeekBar seekBar) {
                                        android.provider.Settings.System.putInt(getContentResolver(),
                                                android.provider.Settings.System.SCREEN_BRIGHTNESS,
                                                progress);
                                    }
                                });
                            }
                        }

                );

        // Création
        AlertDialog alertDialog = alertDialogBuilder.create();

        // Affichage
        alertDialog.show();
    }


    /**
     * Fonction permettant l'actualisation de façon dynamique les mesures des blcos
     */
    public void timer() {
        // Création du timer
        CountDownTimer timer = new CountDownTimer(5000, 2000) {
            public void onTick(long millisUntilFinished) {
                // Pour chaque étape du timer, faire telle action
            }

            @Override
            public void onFinish() {
                // Lorsque le temps est écoulé, faire ces actions
                TextView Bloc_1;
                // Récupération du BLOC 1 créé en XML
                Bloc_1 = (TextView) findViewById(R.id.Bloc_1);
                Bloc_1.setText("Hauteur d'eau :\n" +
                        "        15486,2 µm\n" +
                        "Concentration salinité NaCl² :\n" +
                        "        45 %\n" +
                        "Concentration salinité MgCl² :\n" +
                        "        24 %");

                TextView Bloc_2;
                // Récupération du BLOC 2 créé en XML
                Bloc_2 = (TextView) findViewById(R.id.Bloc_2);
                Bloc_2.setText("Vitesse Vent Moy 10 :\n" +
                        "        0,25 m/s\n" +
                        "Direction Vent Moy 10' :\n" +
                        "        278,5°C\n" +
                        "Wind Speed Max1' :\n" +
                        "        125,56 m/s");

                TextView Bloc_3;
                // Récupération du BLOC 3 créé en XML
                Bloc_3 = (TextView) findViewById(R.id.Bloc_3);
                Bloc_3.setText("Rayonnement Direct :\n" +
                        "        3 000 J/m²\n" +
                        "Rayonnement Diffus :\n" +
                        "        1 000 J/m²\n" +
                        "Rayonnement global :\n" +
                        "        2 500 J/m²");

                TextView Bloc_4;
                // Récupération du BLOC 4 créé en XML
                Bloc_4 = (TextView) findViewById(R.id.Bloc_4);
                Bloc_4.setText("Cumul précipitation 1' :\n" +
                        "        22,05 mm\n" +
                        "Intensité précipitation 1' :\n" +
                        "        2,58 mm/h\n" +
                        "Type précipation 1' :\n" +
                        "        1");

                TextView Bloc_5;
                // Récupération du BLOC 5 créé en XML
                Bloc_5 = (TextView) findViewById(R.id.Bloc_5);
                Bloc_5.setText("Cumul précipitation 1' :\n" +
                        "        22,05 mm\n" +
                        "Intensité précipitation 1' :\n" +
                        "        2,58 mm/h\n" +
                        "Type précipation 1' :\n" +
                        "        1");

                TextView Bloc_6;
                // Récupération du BLOC 6 créé en XML
                Bloc_6 = (TextView) findViewById(R.id.Bloc_6);
                Bloc_6.setText("Rayonnement Direct :\n" +
                        "        3 000 J/m²\n" +
                        "Rayonnement Diffus :\n" +
                        "        1 000 J/m²\n" +
                        "Rayonnement global :\n" +
                        "        2 500 J/m²");

                TextView Bloc_7;
                // Récupération du BLOC 7 créé en XML
                Bloc_7 = (TextView) findViewById(R.id.Bloc_7);
                Bloc_7.setText("Rayonnement Direct :\n" +
                        "        3 000 J/m²\n" +
                        "Rayonnement Diffus :\n" +
                        "        1 000 J/m²\n" +

                        "Rayonnement global :\n" +
                        "        2 500 J/m²");

                TextView Bloc_8;
                // Récupération du BLOC 8 créé en XML
                Bloc_8 = (TextView) findViewById(R.id.Bloc_8);
                Bloc_8.setText("Tension d'alim :\n" +
                        "        12,5 V\n" +
                        "Température interne :\n" +
                        "        54,2 °C\n" +
                        "Pourcentage charge batterie :\n" +
                        "        34 %");

                // Appel du second timer
                timer_b();
            }
        // Demarrage du timer
        }.start();
    }

    /**
     * Fonction permettant l'actualisation de façon dynamique les mesures des blcos
     */
    public void timer_b() {
        // Création du timer
        final CountDownTimer timer = new CountDownTimer(5000, 2000) {

            public void onTick(long millisUntilFinished) {
                // Pour chaque étape du timer, faire telle action
            }

            @Override
            public void onFinish() {
                // Lorsque le temps est écoulé, faire ces actions
                    TextView Bloc_1;
                    // Récupération du BLOC 1 créé en XML
                    Bloc_1 = (TextView) findViewById(R.id.Bloc_1);
                    Bloc_1.setText("Cumul précipitation 1' :\n" +
                            "        22,05 mm\n" +
                            "Intensité précipitation 1' :\n" +
                            "        2,58 mm/h\n" +
                            "Type précipation 1' :\n" +
                            "        1");

                    TextView Bloc_2;
                    // Récupération du BLOC 2 créé en XML
                    Bloc_2 = (TextView) findViewById(R.id.Bloc_2);
                    Bloc_2.setText("Vitesse Vent Moy 10' :\n" +
                            "        0,25 m/s\n" +
                            "Direction Vent Moy 10' :\n" +
                            "        10°C\n" +
                            "Wind Speed Max1' :\n" +
                            "        125,56 m/s");

                    TextView Bloc_3;
                    // Récupération du BLOC 3 créé en XML
                    Bloc_3 = (TextView) findViewById(R.id.Bloc_3);
                    Bloc_3.setText("Pression absolu :\n" +
                            "        856,4 hPa\n" +
                            "Coefficient de friction :\n" +
                            "        0,22\n" +
                            "Concentration glace :\n" +
                            "        88%");

                    TextView Bloc_4;
                    // Récupération du BLOC 4 créé en XML
                    Bloc_4 = (TextView) findViewById(R.id.Bloc_4);
                    Bloc_4.setText("Hauteur d'eau :\n" +
                            "        15486,2 µm" +
                            "Concentration salinité NaCl² :\n" +
                            "        45 %\n" +
                            "Concentration salinité MgCl² :\n" +
                            "        24 %");

                    TextView Bloc_5;
                    // Récupération du BLOC 5 créé en XML
                    Bloc_5 = (TextView) findViewById(R.id.Bloc_5);
                    Bloc_5.setText("Hauteur d'eau :\n" +
                            "        15486,2 µm\n\n" +
                            "Concentration salinité NaCl² :\n" +
                            "        45 %\n" +
                            "Concentration salinité MgCl² :\n" +
                            "        24 %");

                    TextView Bloc_6;
                    // Récupération du BLOC 6 créé en XML
                    Bloc_6 = (TextView) findViewById(R.id.Bloc_6);
                    Bloc_6.setText("Vitesse Vent Moy 10' :\n" +
                            "        0,25 m/s\n" +
                            "Direction Vent Moy 10' :\n" +
                            "        278,5°Cn\n" +
                            "Wind Speed Max1' :\n" +
                            "        125,56 m/s");

                    TextView Bloc_7;
                    // Récupération du BLOC 7 créé en XML
                    Bloc_7 = (TextView) findViewById(R.id.Bloc_7);
                    Bloc_7.setText("Vitesse Vent Moy 10' :\n" +
                            "        0,25 m/s\n" +
                            "Direction Vent Moy 10' :\n" +
                            "        278,5°Cn\n" +
                            "Wind Speed Max1' :\n" +
                            "        125,56 m/s");

                    TextView Bloc_8;
                    // Récupération du BLOC 8 créé en XML
                    Bloc_8 = (TextView) findViewById(R.id.Bloc_8);
                    Bloc_8.setText("Cumul précipitation 1' :\n" +
                            "        22,05 mm\n" +
                            "Intensité précipitation 1' :\n" +
                            "        2,58 mm/h\n" +
                            "Type précipation 1' :\n" +
                            "        1");

                    // Appel du second timer
                    timer();
            }
            // Demarrage du timer
        }.start();
    }


    /**
     * Désactive le boutton de la navBar BACK
     * @param keyCode
     * @param event
     * @return false
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK)
            Toast.makeText(getApplicationContext(), "Boutton logique BACK détecté et désactivé",
                    Toast.LENGTH_SHORT).show();
        return false;
    }


    /**
     * Désactive le boutton de la navBar MENU
     * @param menu
     * @return false
     */
    @Override
    public boolean onPrepareOptionsMenu (Menu menu) {
        Toast.makeText(getApplicationContext(), "Boutton logique MENU détecté et désactivé", Toast.LENGTH_LONG).show();
    return false;
    }

}