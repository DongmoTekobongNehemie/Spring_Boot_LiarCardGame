let socket = new WebSocket("https://liarcardgame.onrender.com/game");
//ws://localhost:8080/game
socket.onopen = function(event) {
	console.log("WebSocket is open now.");
};


let constestable = false;
socket.onmessage = function(event) {

	let message = JSON.parse(event.data);
	console.log(message);

	let roomCreated = '<i class="fa-solid fa-circle-xmark"></i>\n' + message.body;
	let creation_de_joueur = '<i class="fa-solid fa-user-crown"></i>\n' + message.body;
	let full_session = '<i class="fa-solid fa-user-crown"></i>\n' + message.body;
	let vous_avez_jouez = '<i class="fa-solid fa-user-crown"></i>\n' + message.body;
	let ne_pouvez_pas_contester = '<i class="fa-light fa-hand"></i>\n' + message.body;
	let actuellemt_le_tour = '<i class="fa-regular fa-circle-check"></i>\n' + message.body;
	let perosnne_ne_conteste = '<i class="fa-regular fa-circle-check"></i>\n' + message.body;

	if(message.type=="Reset"){
		const listContainer = document.querySelector('.list');

		// Vider le contenu existant de la liste
		listContainer.innerHTML = '';
		showToast(perosnne_ne_conteste);
	}
	
	
	if(message.type == "not your turn"){
		showToast(perosnne_ne_conteste);
	}
	
	if(message.type=="gagnant"){
		showToast(perosnne_ne_conteste);

	}
	
	
	if (message.type == "vous ne pouvez pas contester") {
		showToast(ne_pouvez_pas_contester);
	}

	if (message.type == "actuellement le tour") {
		showToast(actuellemt_le_tour);
	}

	if (message.type == "creation de joueur" || message.type == "changement d'identifiant") {
		document.getElementById("name").textContent = message.namePlayer;
		showToast(creation_de_joueur);
	}

	if (message.type == "roomCreated") {
		showToast(roomCreated);
	}

	
	
	if (message.type == "voulez-vous contester ?") {
		
		constestable = true
		showToast(perosnne_ne_conteste);
	}
	

	if (message.type == "Personne ne conteste") {
		showToast(perosnne_ne_conteste);
	}


	if (message.type == "prend les cartes") {
		showToast(perosnne_ne_conteste);
	}


	if (message.type == "roomkey") {
		document.getElementById("roomKeyDisplay").innerHTML = '<img class="share" src="/images/share.png">' + message.body;
	}

	if (message.type == "gameok") {
		console.log("ok changement de page");
		document.getElementById("acceuil").style.display = "none";
		document.getElementById("game").style.display = "block";
	}

	if (message.type == "full") {
		showToast(full_session);
	}

	if (message.type == "are you ready ?") {
		let userResponse;
		do {
			userResponse = confirm("Êtes-vous pret pour commencer 🏁 ?");
		} while (userResponse === false);

		if (userResponse === true) {
			socket.send(roomKey + "-oui");
			console.log(roomKey + "-oui");
		}
	}

	if (message.type == "vous avez jouez") {
		showToast(vous_avez_jouez);
	}

	if (message.type == "CARD") {

		const listContainer = document.querySelector('.list');

		// Vider le contenu existant de la liste
		listContainer.innerHTML = '';

		// Vérifier que le type est bien "CARD" avant de traiter
		if (message.type === "CARD") {
			// Parcourir le tableau des cartes et créer un élément pour chaque carte
			message.cards.forEach(card => {
				// Créer un div pour la carte
				const cardDiv = document.createElement('div');
				cardDiv.classList.add('card');

				// Créer l'input radio
				const input = document.createElement('input');
				input.type = 'radio';
				input.name = 'carte';
				input.classList.add('valid');

				// Créer l'image (Vous pouvez modifier la source selon votre besoin)
				const img = document.createElement('img');
				img.src = '/images/poker.png';
				img.alt = '';
				img.classList.add('ing');

				// Créer le paragraphe pour le pattern
				const pattern = document.createElement('p');
				pattern.classList.add('pattern');
				pattern.textContent = card.pattern;

				// Créer le paragraphe pour le numéro
				const numero = document.createElement('p');
				numero.classList.add('numero');
				numero.textContent = card.number;

				// Ajouter tous les éléments à la carte
				cardDiv.appendChild(input);
				cardDiv.appendChild(img);
				cardDiv.appendChild(pattern);
				cardDiv.appendChild(numero);

				// Ajouter la carte au conteneur de la liste
				listContainer.appendChild(cardDiv);
			});
		}

		if (message.currentCard != null) {
			document.getElementById("numeroofcardplay").textContent = message.currentCard.number;
			document.getElementById("patternofcardplay").textContent = message.currentCard.pattern;
			document.getElementById("badgePattern").textContent = message.currentPattern;

		}
	}
}

function createRoom() {
	socket.send("createRoom");
}

let roomKey;

function findRoom(event) {
	event.preventDefault(); // Empêche le rechargement de la page

	let key = document.getElementById("inputKey").value;
	socket.send(key);
	roomKey = key;
}

function gameplay(event) {
	event.preventDefault();

	let messagesend = '';

	// Sélectionne le bouton radio qui est coché
	const selectedCard = document.querySelector('input[name="carte"]:checked');

	if (selectedCard) {
		// Trouve la carte parente du bouton radio sélectionné
		const card = selectedCard.closest('.card');

		if (card) {
			// Récupère le numéro et le pattern de la carte
			const numero = card.querySelector('.numero').textContent;
			const pattern = card.querySelector('.pattern').textContent;

			// Récupère la valeur sélectionnée du menu déroulant
			const selectBox = document.querySelector('.select-box');
			const patternPlay = selectBox ? selectBox.value : '';

			// Modifie la variable messagesend avec le format "numero-pattern-patternPlay"
			//messagesend = `${numero}-${pattern}-${patternPlay}`;

			messagesend = `${roomKey}-${numero}-${pattern}-${patternPlay}`;

			// Affiche la valeur de messagesend dans la console pour vérification
			console.log(messagesend);

			// Envoie le message via la WebSocket
			socket.send(messagesend);
		}
	} else {
		console.log('Aucune carte sélectionnée.');
	}
}


function contradict(event){
	
	event.preventDefault();
	if(constestable ==true){
	socket.send(roomKey+"=moi");
	console.log(roomKey+"=moi");
	}
}
/*socket.onclose = function(event) {
	
	socket.send("")
	
}
*/
function showToast(msg) {
	let toastBox = document.getElementById('toastBox');
	let toast = document.createElement('div');
	toast.classList.add('toast');
	toast.innerHTML = msg;
	toastBox.appendChild(toast);
	// Ajouter la classe success à toutes les notifications
	toast.classList.add('success');
	setTimeout(() => {
		toast.remove();
	}, 3000);
}