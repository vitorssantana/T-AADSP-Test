
package controller;

import java.io.IOException;
import java.util.List;

import db.StakeholderDAO;
import model.Stakeholder;

public class StakeholderController {

	private StakeholderDAO dao;

	public StakeholderController() throws IOException {
		dao = new StakeholderDAO();
	}

	public List<Stakeholder> enviarListaStakeholder() {
		return dao.retornarListaStakeholders();
	}

	public void addNewStakeholder(Stakeholder stakeholder) {
		dao.addNewStakeholder(stakeholder);
	}

	public void editarStakeholder(Stakeholder stakeholder) {
		dao.editarDadosStakeholder(stakeholder);
	}
	
	public void removerStakeholder(Stakeholder stakeholder) {
		dao.removerStakeholder(stakeholder);
	}

}
