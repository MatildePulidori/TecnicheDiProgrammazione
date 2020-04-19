# Indovina Numero

Realizzare un programma `JavaFX` in grado di permettere all'utente di giocare ad "_indovina il numero_".

In ogni partita, il programma deve inventare un numero casuale tra 1 e `NMAX` (estremi compresi), e l'utente deve tentare di indovinare il numero segreto. Ad ogni tentativo, il programma potrà rispondere in tre modi: "Numero esatto" (ed in tal caso la partita termina), "Troppo basso", "Troppo alto".

L'utente ha un numero di tentativi limitato `TMAX` per poter indovinare il numero segreto. 
In ogni momento, il programma visualizza in numero di tentativi ancora disponibili. 

Una partita può terminare perché il numero è stato indovinato, oppure perché sono stati esauriti tutti i tentativi disponibili (ed in questo caso il programma mostra il numero segreto). Al termine di una partita se ne può iniziare una nuova, con un nuovo numero casuale.

## Estensioni possibili

1. In qualsiasi momento, l'utente può decidere di abbandonare la partita (per eventualmente ricominciarne una nuova).

1. Lo stato di avanzamento (numero di tentativi già fatti rispetto a quelli totali) viene indicato con una ProgressBar.

1. All'inizio della partita, l'utente può selezionare tra diversi livelli di difficoltà (ad esempio: Facile, Medio, Difficile). Ogni livello di difficoltà corrisponde ad un determinato valore di `NMAX` e di `TMAX`. 
  _Nota_: si consiglia di impostare `TMAX` nell'ordine di Log_2(`NMAX`). Perché? 

1. È possibile giocare in modalità "_assistita_", nella quale il programma mostra l'intervallo entro cui deve giacere il numero segreto, tenendo conto dei tentativi già fatti dall'utente, aggiornandolo ad ogni nuovo tentativo.

1. Il programma impedisce che l'utente possa inserire più di una volta lo stesso numero.

## Funziona, ma...

* Codice duplicato

* Impossibile da testare automaticamente (serve un giocatore umano)

* Mix di codice relativo all'interfaccia con codice relativo all'algoritmo

* Impossibile, in futuro, usare un'interfaccia diversa (console, web, touch, vocale, ...)