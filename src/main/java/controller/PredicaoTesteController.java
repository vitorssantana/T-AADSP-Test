package controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import bn.BayesianNetwork;
import db.PredicaoTesteDAO;
import model.PredicaoTeste;
import model.Requisito;
import model.Sprint;
import utils.PredicaoTesteUtils;

public class PredicaoTesteController {

	private PredicaoTesteUtils predicaoTesteUtils;
	private PredicaoTesteDAO dao;

	public PredicaoTesteController() throws IOException {

		dao = new PredicaoTesteDAO();
	}

	public void realizarPredicao(PredicaoTeste predicaoTeste, Requisito requisito, Sprint sprint)
			throws IOException, ParseException {
		predicaoTesteUtils = new PredicaoTesteUtils(sprint, requisito);
		double[] notas = predicaoTesteUtils.retornarListaProbabilidade();

		predicaoTeste.setProbabilidadeAlta(notas[0]);
		predicaoTeste.setProbabilidadeMedia(notas[1]);
		predicaoTeste.setProbabilidadeBaixa(notas[2]);
		dao.addPredicoesTeste(predicaoTeste);
	}

	public List<PredicaoTeste> retornarListaPredicao() {
		return dao.retornarListaPredicoesTeste();
	}

}
