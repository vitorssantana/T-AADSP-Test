
package controller;

import java.io.IOException;
import java.util.List;

import db.StakeholderDAO;
import model.Stakeholder;

public class StakeholderController {

	private StakeholderDAO stakeholderDAO;

	public StakeholderController() throws IOException {
		stakeholderDAO = new StakeholderDAO();
	}

	public List<Stakeholder> enviarListaStakeholder() {
		return stakeholderDAO.retornarListaStakeholders();
	}
	
	public void addNewStakeholder(Stakeholder stakeholder) {
		stakeholderDAO.addNewStakeholder(stakeholder);
	}

}
