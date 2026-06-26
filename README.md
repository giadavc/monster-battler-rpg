# 🎮 Monster Battler RPG

Monster Battler RPG è un gioco di ruoli in cui il giocatore sceglie un nome e una creatura iniziale, poi affronta battaglie su due fasi narrative (Early Game e Late Game), sblocca nuove creature progredendo e può salvare e ricaricare la partita in qualsiasi momento. 

---

## 🚀 Come eseguire il progetto

### Prerequisiti
- Java 25
- Gradle 9.2.0

### Istruzioni

```bash
git clone https://github.com/giadavc/monster-battler-rpg.git
cd monster-battler-rpg
```

### Build del progetto
```bash
./gradlew build
```

### Esecuzione
```bash
./gradlew run
```

---

## 🤖 Uso di strumenti di AI

Durante lo sviluppo ho fatto ricorso a strumenti di intelligenza artificiale (**Claude** e **ChatGPT**) come **supporto** in specifiche fasi del lavoro, mantenendo sempre il controllo diretto sul codice e sulle scelte progettuali.

### Claude

Claude è stato utilizzato principalmente come supporto tecnico durante lo sviluppo:

* **Strutturazione iniziale del progetto** — per comprendere come organizzare correttamente l'architettura dell'applicazione, la suddivisione in package e la gestione dei diversi componenti del progetto JavaFX.

* **Supporto nella scrittura di codice JavaFX** — nei punti in cui non riuscivo a capire come realizzare determinati componenti dell'interfaccia o come utilizzare alcune funzionalità del framework, ho richiesto spiegazioni ed esempi successivamente adattati alle esigenze del progetto.

* **Correzione di errori** — per alcuni errori di compilazione o comportamenti inattesi a runtime, ho utilizzato Claude per individuare possibili cause del problema e ragionare sulle relative soluzioni.

### ChatGPT

ChatGPT è stato utilizzato principalmente come supporto concettuale e creativo:

* **Approfondimento concettuale** — per comprendere meglio il funzionamento della persistenza dei dati (serializzazione/deserializzazione JSON con Gson, gestione dei file di salvataggio) e di altri concetti utilizzati nel progetto a livello teorico.

* **Testi di gioco** — per ottenere suggerimenti riguardanti descrizioni narrative, storia introduttiva, nomi delle creature e testi presenti nei menu, successivamente selezionati e modificati in base al tono desiderato.

**Ogni soluzione proposta è stata attentamente analizzata, compresa e modificata prima di essere integrata nel progetto.** Gli strumenti di AI hanno svolto il ruolo di supporto allo studio, al confronto e alla risoluzione di problemi.

---

📌 Per una descrizione più dettagliata dell'utilizzo dell'AI, consultare la **Wiki del repository**.
