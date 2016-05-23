var myMap = new Map();
var titleInput;
var authorInput;
var numberInput;
var photoInput;
var idEdit;
var book;

function Book(title, author, numberPages, cover) {
	this.id = "1";
	this.title = title;
	this.author = author;
	this.numberPages = numberPages;
	this.cover = cover;
	this.toJsonString = function() {
		return JSON.stringify(this);
	};
}

function insert() {

	titleInput = document.myForm.book_title.value;
	authorInput = document.myForm.book_author.value;
	numberInput = document.myForm.book_pages_number.value;
	photoInput = $('#image_file').get(0);

	submitDataWithCover(titleInput, authorInput, numberInput, photoInput,
			"insert");

}

function submitDataWithCover(titleInput, authorInput, numberInput, photoInput,
		functionType) {
	var reader = new FileReader();

	reader.onload = function(e) {
		var photoBase64 = e.target.result;
		book = new Book(titleInput, authorInput, numberInput, photoBase64);

		if (functionType == "insert") {
			$.ajax({
				type : "POST",
				url : "http://localhost:8080/book/",
				contentType : "application/json;charset=UTF-8",
				dataType : 'json',
				data : book.toJsonString()
			}).then(function(data, status, jqxhr) {
				if (status == 'success') {
					window.location.reload();
				} else {
					alert(status);
				}

			});

		} else if (functionType == "update") {
			// here we update the books catalog
			$.ajax({
				type : "PUT",
				url : "http://localhost:8080/book/" + idEdit,
				contentType : "application/json;charset=UTF-8",
				dataType : 'json',
				data : book.toJsonString()
			}).then(function(data, status, jqxhr) {
				if (status == 'success') {
					window.location.reload();
				} else {
					alert(status);
				}

			});
		}
	}

	reader.readAsDataURL(photoInput.files[0]);

}

function delete_row(index) {

	$.ajax({
		type : "DELETE",
		url : "http://localhost:8080/book/" + index,
		contentType : "application/json;charset=UTF-8",
		dataType : 'json'
	}).then(function(data, status, jqxhr) {
		console.log(status);
		if (status == 'success') {

			window.location.reload();
		}
	});
}

function addElementToTable(book) {
	var tbl = document.getElementById('myTable').getElementsByTagName('tbody')[0];
	;
	var lastRow = tbl.rows.length;
	// if there's no header row in the table, then iteration = lastRow + 1
	var row = tbl.insertRow(lastRow);
	// cells
	var cell0 = row.insertCell(0);
	var cell1 = row.insertCell(1);
	var cell2 = row.insertCell(2);
	var cell3 = row.insertCell(3);
	var cell4 = row.insertCell(4);
	var cell5 = row.insertCell(5);

	var titleNode = document.createTextNode(book.title);
	var authorNode = document.createTextNode(book.author);
	var numberNode = document.createTextNode(book.numberPages);

	var photoNode = document.createElement('img');
	photoNode.setAttribute('src', book.cover);
	photoNode.setAttribute('onClick', "showCover(" + book.id + ");");
	photoNode.setAttribute('data-toggle', "modal");
	photoNode.setAttribute('data-target', "#coverModal");
	photoNode.width = '30';
	photoNode.height = '30';

	var linkEdit = document.createElement('img');
	linkEdit.setAttribute('onClick', "edit(" + book.id + "); return false;");
	linkEdit.setAttribute('src', 'img/pen.png');
	linkEdit.setAttribute('data-toggle', "modal");
	linkEdit.setAttribute('data-target', "#editModal");
	linkEdit.width = '20';
	linkEdit.height = '20';

	var linkDelete = document.createElement('img');
	linkDelete.setAttribute('onClick', "delete_row(" + book.id
			+ "); return false;");
	linkDelete.setAttribute('src', 'img/trash.png');
	linkDelete.width = '20';
	linkDelete.height = '20';

	cell0.appendChild(titleNode);
	cell1.appendChild(authorNode);
	cell2.appendChild(numberNode);
	if (book.cover != null) {
		cell3.appendChild(photoNode);
	}
	cell4.appendChild(linkEdit);
	cell5.appendChild(linkDelete);
}

// When the user clicks on the button, open the modal
function edit(id) {
	idEdit = id;
	var titlev = myMap.get(id).title;
	var authorv = myMap.get(id).author;
	var numPagesv = myMap.get(id).numberPages;

	$('#editModal').find('[name="update_title"]').val(titlev);
	$('#editModal').find('[name="update_author"]').val(authorv);
	$('#editModal').find('[name="update_pages_number"]').val(numPagesv);

}

function showCover(id) {
	var image = myMap.get(id).cover;
	$('#coverModal').find('[name="image_cover"]').attr("src", image);

}

function update() {
	var titleInput = $('#editModal').find('[name="update_title"]').val();
	var authorInput = $('#editModal').find('[name="update_author"]').val();
	var numberInput = $('#editModal').find('[name="update_pages_number"]')
			.val();
	var photoInput = $('#editModal').find('[name="image_file_update"]').get(0);

	if (photoInput.files && photoInput.files[0]) {
		submitDataWithCover(titleInput, authorInput, numberInput, photoInput,
				"update");
	} else {

		var oldImage = myMap.get(idEdit).cover;
		book = new Book(titleInput, authorInput, numberInput, oldImage);
		// here we update the telephone directory
		$.ajax({
			type : "PUT",
			url : "http://localhost:8080/book/" + idEdit,
			contentType : "application/json;charset=UTF-8",
			dataType : 'json',
			data : book.toJsonString()
		}).then(function(data, status, jqxhr) {
			if (status == 'success') {
				window.location.reload();
			} else {
				alert(status);
			}

		});
	}

}

$(document).ready(function() {
	$.ajax({
		type : "GET",
		url : "http://localhost:8080/book/",
		contentType : "application/json;charset=UTF-8",
		dataType : 'json'
	}).then(function(data, status, jqxhr) {
		if (status == 'success') {
			if (data)
				for (var j = 0; j < data.length; j++) {
					var id = parseInt(data[j].id);
					myMap.set(data[j].id, data[j]);
					addElementToTable(data[j]);
				}

			$('#myTable').dataTable();

		}

	});
});
