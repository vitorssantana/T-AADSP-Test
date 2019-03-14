package utils;

import java.io.IOException;
import java.io.ObjectInputFilter.Status;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import bn.BayesianNetwork;
import controller.DesenvolvedorController;
import controller.DesenvolvedorRequisitoSprintController;
import controller.ProjetoController;
import controller.RequisitoController;
import controller.RequisitoSprintController;
import controller.SprintController;
import controller.StakeholderController;
import controller.StatusCasosTesteController;
import db.SprintDAO;
import db.StatusCasosTesteDAO;
import model.Desenvolvedor;
import model.DesenvolvedorRequisitoSprint;
import model.Projeto;
import model.Requisito;
import model.RequisitoSprint;
import model.Sprint;
import model.Stakeholder;
import model.StatusCasosTeste;

public class PredicaoTesteUtils {

	private BayesianNetwork bn;

	private Sprint sprint;
	private Projeto projeto;
	private Stakeholder stakeholderProjeto;
	private Stakeholder stakeholderRequisito;
	private RequisitoSprint requisitoSprint;
	private DesenvolvedorRequisitoSprint desenvolvedorRequisitoSprint;
	private Requisito requisito;
	String alteracoesRequisito, nivelDesenvolvedores, porctCobertTestesAnterior, nivelImporStakeholderProjeto,
			nivelImportanciaStakeholderRequisito, custoProjeto, deadlineProjeto;

	public PredicaoTesteUtils(Sprint sprint, Requisito requisito) throws IOException, ParseException {
		this.sprint = sprint;
		this.requisito = requisito;

		definirEntradaNoAlteracoesRequisito();
		definirEntradaNoMediaNivelDesenvolvedor();
		definirEntradaNoPorcentagemExecucaoSprintAnterior();
		definirEntradaNoNivelImportanciaStakeholderProjeto();
		definirEntradaNoNivelImportanciaStakeholderRequisito();
		definirEntradaNoCustoProjeto();
		definirEntradaNoDeadlineProjeto();
	}

	public void definirEntradaNoAlteracoesRequisito() throws IOException {
		List<RequisitoSprint> listaRequisitoSprint = new RequisitoSprintController().retornarListaRequisitoSprint();

		for (RequisitoSprint requisitoSprint : listaRequisitoSprint) {
			if (requisitoSprint.getIdRequisito().equals(requisito.getId())
					&& requisitoSprint.getIdSprint() == sprint.getId()) {
				this.requisitoSprint = requisitoSprint;
			}
		}

		if (requisitoSprint.getNivelImpactoAlteracoes().equals("Alto")) {
			alteracoesRequisito = "Alto";
		} else if (requisitoSprint.getNivelImpactoAlteracoes().equals("Médio")) {
			alteracoesRequisito = "Médio";
		} else {
			alteracoesRequisito = "Baixo";
		}
	}

	public void definirEntradaNoMediaNivelDesenvolvedor() throws IOException {
		double notaDesenvolvedores = 0;
		List<Desenvolvedor> listaDesenvolvedor = new DesenvolvedorController().enviarListaDesenvolvedor();
		List<Desenvolvedor> listaDesenvolvedorVinculados = new ArrayList<Desenvolvedor>();
		List<DesenvolvedorRequisitoSprint> listaDevReqSprint = new DesenvolvedorRequisitoSprintController()
				.retornarListaDesenvolvedorRequisitoSprint();
		List<DesenvolvedorRequisitoSprint> listaDevReqSprintVinculados = new ArrayList<DesenvolvedorRequisitoSprint>();

		for (DesenvolvedorRequisitoSprint devReqSprint : listaDevReqSprint) {
			if (devReqSprint.getIdRequisitoSprint() == requisitoSprint.getId()) {
				listaDevReqSprintVinculados.add(devReqSprint);
			}
		}

		for (DesenvolvedorRequisitoSprint devReqSprint : listaDevReqSprintVinculados) {
			for (Desenvolvedor desenvolvedor : listaDesenvolvedor) {
				if (desenvolvedor.getId() == devReqSprint.getIdDesenvolvedor()) {
					listaDesenvolvedorVinculados.add(desenvolvedor);
					notaDesenvolvedores = notaDesenvolvedores + desenvolvedor.getNota()
							+ Integer.valueOf(devReqSprint.getNivelParticipacao());
				}
			}
		}

		if (notaDesenvolvedores < 5.0) {
			nivelDesenvolvedores = "PoucoConfiavel";
		} else if (notaDesenvolvedores >= 5.0 && notaDesenvolvedores < 7.0) {
			nivelDesenvolvedores = "Confiavel";
		} else {
			nivelDesenvolvedores = "MuitoConfiavel";
		}
	}

	public void definirEntradaNoPorcentagemExecucaoSprintAnterior() throws IOException {
		List<Sprint> listaSprint = new SprintController().enviarListaSprint();

		if (listaSprint.size() == 1) {
			porctCobertTestesAnterior = "CoberturaMinima";
			return;
		}

		List<StatusCasosTeste> listaStatusCasosTestes = new StatusCasosTesteController()
				.retornarListaStatusCasosTeste();
		List<StatusCasosTeste> listaStatusCasosTestesSelecionados = new ArrayList<StatusCasosTeste>();
		List<RequisitoSprint> listaRequisitoSprint = new RequisitoSprintController().retornarListaRequisitoSprint();

		for (int i = listaRequisitoSprint.size(); i >= 0; i--) {
			if (listaRequisitoSprint.get(i).getIdRequisito() == requisito.getId()
					&& listaRequisitoSprint.get(i).getId() < requisitoSprint.getId()) {

				for (StatusCasosTeste statusCasosTeste : listaStatusCasosTestes) {
					if (statusCasosTeste.getIdRequisitoSprint() == listaRequisitoSprint.get(i).getId()) {
						listaStatusCasosTestesSelecionados.add(statusCasosTeste);
					}
				}

				int contador = 0;
				for (StatusCasosTeste statusCasosTeste : listaStatusCasosTestesSelecionados) {
					if (statusCasosTeste.getStatus() == "Sucesso") {
						contador++;
					}
				}

				int porcentagemCoberturaTeste = (100 * contador) / listaStatusCasosTestesSelecionados.size();

				if (porcentagemCoberturaTeste < 40) {
					porctCobertTestesAnterior = "CoberturaMinima";
				} else if (porcentagemCoberturaTeste >= 40 && porcentagemCoberturaTeste < 70) {
					porctCobertTestesAnterior = "CoberturaMedia";
				} else {
					porctCobertTestesAnterior = "CoberturaAlta";
				}

				return;

			} else if (listaRequisitoSprint.get(i).getIdRequisito() != requisito.getId() && i == 0) {
				porctCobertTestesAnterior = "CoberturaMinima";
				return;
			}
		}

	}

	public void definirEntradaNoNivelImportanciaStakeholderProjeto() throws IOException {
		List<Stakeholder> listaStakeholders = new StakeholderController().enviarListaStakeholder();
		List<Projeto> listaProjeto = new ProjetoController().enviarListaProjetos();
		Projeto projetoSelecionado = null;
		Stakeholder stakeholderSelecionado = null;

		for (Projeto projeto : listaProjeto) {
			if (projeto.getId() == requisito.getIdProjeto()) {
				projetoSelecionado = projeto;
			}
		}

		for (Stakeholder stakeholder : listaStakeholders) {
			if (projetoSelecionado.getIdStakeholder() == stakeholder.getId()) {
				stakeholderSelecionado = stakeholder;
			}
		}

		if (stakeholderSelecionado.getNotaPrioridade() < 5) {
			nivelImporStakeholderProjeto = "Baixa";
		} else if (stakeholderSelecionado.getNotaPrioridade() >= 5 && stakeholderSelecionado.getNotaPrioridade() < 7) {
			nivelImporStakeholderProjeto = "Media";
		} else {
			nivelImporStakeholderProjeto = "Alta";
		}
	}

	public void definirEntradaNoNivelImportanciaStakeholderRequisito() throws IOException {
		List<Stakeholder> listaStakeholders = new StakeholderController().enviarListaStakeholder();
		Stakeholder stakeholderSelecionado = null;

		for (Stakeholder stakeholder : listaStakeholders) {
			if (requisito.getIdStakeholder() == stakeholder.getId()) {
				stakeholderSelecionado = stakeholder;
			}
		}

		if (stakeholderSelecionado.getNotaPrioridade() < 5) {
			nivelImportanciaStakeholderRequisito = "Baixa";
		} else if (stakeholderSelecionado.getNotaPrioridade() >= 5 && stakeholderSelecionado.getNotaPrioridade() < 7) {
			nivelImportanciaStakeholderRequisito = "Media";
		} else {
			nivelImportanciaStakeholderRequisito = "Alta";
		}

	}

	public void definirEntradaNoCustoProjeto() throws IOException {
		List<Projeto> listaProjeto = new ProjetoController().enviarListaProjetos();
		Projeto projetoSelecionado = null;

		for (Projeto projeto : listaProjeto) {
			if (projeto.getId() == requisito.getIdProjeto()) {
				projetoSelecionado = projeto;
			}
		}

		if (projetoSelecionado.getCusto() < 30000.00) {
			custoProjeto = "Baixo";
		} else if (projetoSelecionado.getCusto() >= 30000.00 && projetoSelecionado.getCusto() < 100000.00) {
			custoProjeto = "Medio";
		} else {
			custoProjeto = "Alto";
		}
	}

	public void definirEntradaNoDeadlineProjeto() throws IOException, ParseException {
		List<Projeto> listaProjeto = new ProjetoController().enviarListaProjetos();
		Projeto projetoSelecionado = null;

		for (Projeto projeto : listaProjeto) {
			if (projeto.getId() == requisito.getIdProjeto()) {
				projetoSelecionado = projeto;
			}
		}

		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
		Date dataEntregaProjeto = formato.parse(projetoSelecionado.getPrazoEntrega());
		Date dataAtual = new Date(System.currentTimeMillis());

		int diferencaMes = DateUtils.calcularDiferencaDeMeses(dataAtual, dataEntregaProjeto);

		if (diferencaMes < 3) {
			deadlineProjeto = "Curto";
		} else if (diferencaMes >= 3 && diferencaMes < 6) {
			deadlineProjeto = "Medio";
		} else {
			deadlineProjeto = "Longo";
		}
	}

	public double[] retornarListaProbabilidade() {
		return bn.realizarPredicao(alteracoesRequisito, nivelDesenvolvedores, porctCobertTestesAnterior,
				nivelImporStakeholderProjeto, nivelImportanciaStakeholderRequisito, custoProjeto, deadlineProjeto);
	}

}
