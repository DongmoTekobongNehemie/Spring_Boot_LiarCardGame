function createRoom() {
	const room = {};

	fetch("http://localhost:8080/liarGameRoom", {
		method: "POST",
		headers: {
			"Content-Type": "application/json"
		},
		body: JSON.stringify(room)
	})
		.then(response => response.json())  // Parse la réponse en JSON
		.then(data => {
			const roomKey = data.roomKey;  // Récupère la valeur de roomKey
			const badgeElement = document.getElementById("roomKeyDisplay");
			badgeElement.innerHTML = `<img class="share" src="/images/share.png">  ${roomKey} `; // Affiche la roomKey avant l'image
		})
		.catch(error => console.error('Error:', error));
}

function findRoom() {
	let keyroom = document.getElementById("inputKey").value;
	console.log(keyroom);

	// Envoi d'une requête GET à l'API pour vérifier si la room existe
	fetch(`http://localhost:8080/liarGameRoom/${keyroom}`)
		.then(response => {
			if (!response.ok) {
				throw new Error(' Room not found');
			}
			return response.json();
		})
		.then(roomExists => {

			if (roomExists) {
				// Si la room existe, rediriger vers la page de jeu avec la roomKey
				window.location.href = `liarGameRoom/${keyroom}/play`;
			} else {
				// Afficher un message d'erreur si la room n'existe pas
				alert('Room not found');
			}
		})
		.catch(error => {
			// Gestion des erreurs réseau ou autres
			alert(error.message);
		});
}
