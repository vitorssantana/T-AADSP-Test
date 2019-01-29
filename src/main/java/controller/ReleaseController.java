package controller;

import java.io.IOException;
import java.util.List;

import db.ReleaseDAO;
import model.Release;

public class ReleaseController {

	
	private ReleaseDAO releaseDAO;

	public ReleaseController() throws IOException {
		releaseDAO = new ReleaseDAO();
	}

	public List<Release> enviarListaRelease() {
		return releaseDAO.retornarListaReleases();
	}
	
	public void addNewRelease(Release release) {
		releaseDAO.addNewRelease(release);
	}
}
