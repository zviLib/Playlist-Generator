package model.server;

public enum Message {
	refresh, // get songs for similarity
	songId,  // choose song from similarity
	popular, // get suggested categories for popular artists
	explore, // get suggested categories for less popular artists
	custom,  // let user get playlist by selected properties
	categorieId, // category selected (on any view)
	close
}
