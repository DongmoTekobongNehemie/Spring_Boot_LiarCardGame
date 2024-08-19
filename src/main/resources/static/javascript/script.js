// Créez une nouvelle connexion WebSocket.
//const socket = new WebSocket(`ws://localhost:8080/liarcardgame/${roomKey}/play`);

// Fonction appelée lors de l'ouverture de la connexion WebSocket.
socket.onopen = function(event) {
	console.log("WebSocket is open now.");
};

// Fonction appelée lors de la fermeture de la connexion WebSocket.
socket.onclose = function(event) {
	console.log("WebSocket is closed now.");
};

// Fonction appelée lors d'une erreur de la connexion WebSocket.
socket.onerror = function(error) {
	console.log("WebSocket error: " + error);
};

// Fonction pour envoyer un message via la WebSocket.
function sendMessage(m) {
	//var s = document.getElementById("number").value;
	//socket.send(s);
	//alert(s);
}


// lorsque un message est recu 
socket.onmessage = function(event) {

	try {
		let message = JSON.parse(event.data);
		console.log(message);

		let perosnne_ne_conteste = '<i class="fa-light fa-face-sad-tear"></i>\n' + message.body;
		let a_toi = '<i class="fa-solid fa-card-club"></i>\n' + message.body;
		let bonne_chance = '<i class="fa-solid fa-stars"></i>\n' + message.body;
		let not_your_turn = '<i class="fa-solid fa-circle-xmark"></i>\n' + message.body;
		let creation_de_joueur = '<i class="fa-solid fa-user-crown"></i>\n' + message.body;
		let ne_pouvez_pas_contester = '<i class="fa-light fa-hand"></i>\n' + message.body;
		let vous_avez_jouez = '		<i class="fa-thin fa-card-heart" style="color: #e67f65;"></i>\n' + message.body;
		let actuellemt_le_tour = '<i class="fa-regular fa-circle-check"></i>\n' + message.body;
		let prend_les_cartes = '<i class="fa-light fa-face-smile-tongue"></i>\n' + message.body;
		let gagnant = '<i class="fa-solid fa-party-horn" style="color: #74C0FC;"></i>\n' + message.body;
		let reset = '<i class="fa-solid fa-party-horn" style="color: #74C0FC;"></i>\n' + message.body;

		if (message.type == "creation de joueur" || message.type == "changement d'identifiant") {
			document.getElementById("name").textContent = message.namePlayer;
			showToast(creation_de_joueur);
		}

		if (message.type == "are you ready ?") {
			let userResponse;
			do {
				userResponse = confirm("Êtes-vous sûr de vouloir continuer ?");
			} while (userResponse === false);

			if (userResponse === true) {
				socket.send("oui");
				console.log("oui");
			}
		}

		if (message.type == "bonne chance") {
			showToast(bonne_chance);
		}

		if (message.type == "Reset") {
			showToast(reset);
		}

		if (message.type == "a toi") {
			showToast(a_toi);
		}


		if (message.type == "vous avez jouez") {
			showToast(vous_avez_jouez);
		}

		if (message.type == "actuellement le tour") {
			showToast(actuellemt_le_tour);
		}

		if (message.type == "not your turn") {
			showToast(not_your_turn);
		}
		if (message.type == "voulez-vous contester ?") {
			let userResponse = confirm("Voulez-vous contestez ?");
			if (userResponse === true) {
				// L'utilisateur a confirmé la contestation
				socket.send("moi");
				console.log("moi");
			} else {
				// L'utilisateur a annulé, ne faites rien
				console.log("Contestation annulée");
			}
		}


		if (message.type == "ne pouvez pas contester") {
			showToast(ne_pouvez_pas_contester);
		}

		if (message.type == "perosnne ne conteste") {
			showToast(perosnne_ne_conteste);
		}

		if (message.type == "prend les cartes") {
			showToast(prend_les_cartes);
		}

		if (message.type == "gagnant") {
			showToast(gagnant);
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

	} catch (e) {
		console.error("Erreur de parsing JSON : ", e);
		console.log("Message reçu : ", event.data);
	}

}

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
			messagesend = `${numero}-${pattern}-${patternPlay}`;

			// Affiche la valeur de messagesend dans la console pour vérification
			console.log(messagesend);

			// Envoie le message via la WebSocket
			socket.send(messagesend);
		}
		
	} else {
		console.log('Aucune carte sélectionnée.');
	}
}
/*} else {
	console.error('Room key not found in URL');
}
});*/



