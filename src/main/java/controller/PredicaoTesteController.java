package controller;

import java.io.IOException;
import java.text.DecimalFormat;
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

		

		predicaoTeste.setProbabilidadeAlta(Math.round(notas[0] * 100) / 100d);
		predicaoTeste.setProbabilidadeMedia(Math.round(notas[1] * 100) / 100d);
		predicaoTeste.setProbabilidadeBaixa(Math.round(notas[2] * 100) / 100d);
		dao.addPredicoesTeste(predicaoTeste);
	}

	public List<PredicaoTeste> retornarListaPredicao() {
		return dao.retornarListaPredicoesTeste();
	}

}
