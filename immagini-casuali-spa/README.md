# SPA immagini casuali

Una SPA (single page application) composta da:
* **frontend** => composto da un unico file: [frontend.html](https://github.com/dropletzz/25-26_5I/blob/main/immagini-casuali-spa/frontend.html)
* **backend** => implementato in Node.js, fornisce le API per gestire una risorsa REST, sta nella cartella [webserver](https://github.com/dropletzz/25-26_5I/tree/main/immagini-casuali-spa/webserver)


## Istruzioni per l'uso

### Con Docker
Dalla cartella principale del progetto eseguire il comando:
```
docker compose up
```

### Senza Docker
Database:
* Far partire un db MySQL sulla porta 3306
* Creare il database usando lo script [create-db.sql](https://github.com/dropletzz/25-26_5I/blob/main/immagini-casuali-spa/create-db.sql)

Webserver:
* Dalla cartella 'webserver' eseguire i seguenti comandi:
* `npm install` (per installare le dipendenze)
* `npx nodemon src/index.js` (per far partire il server)

Frontend:
* aprire il file 'frontend.html' con un browser
