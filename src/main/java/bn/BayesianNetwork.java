package bn;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.recommenders.jayes.BayesNet;
import org.eclipse.recommenders.jayes.BayesNode;
import org.eclipse.recommenders.jayes.inference.IBayesInferer;
import org.eclipse.recommenders.jayes.inference.junctionTree.JunctionTreeAlgorithm;

public class BayesianNetwork {

	private BayesNet rede;
	private BayesNode alteracoesRequisito;
	private BayesNode mediaNivelDesenvolvedor;
	private BayesNode chanceImpactoRequisito;
	private BayesNode porctCoberturaTestesAnteriores;
	private BayesNode riscoDeBugRequisito;

	private BayesNode nivelImportStakeholderProjeto;
	private BayesNode nivelImportStakeholderRequisito;
	private BayesNode nivelHierarquiaRequisito;
	private BayesNode custoProjeto;
	private BayesNode deadlineProjeto;
	private BayesNode prioridadeDoRequisito;
	private BayesNode notaPrioridadeRequisito;
	private BayesNode predicaoFinalRequisito;

	private BayesNode prioridadeDeTesteDoRequisito;

	public BayesianNetwork() {
		rede = new BayesNet();
		iniciarNoChancesImpactoRequisito();
		iniciarNoRiscoDeBugsNoRequisito();
		iniciarNoNivelHierarquiaRequisito();
		iniciarNoPrioridadeDoRequisito();
		iniciarNoPrioridadeTesteDoRequisito();
		iniciarNoNotaPrioridadeReuqisito();
		predicaoFinalRequisito();
	}

	public static void main(String[] args) {
		BayesianNetwork bn = new BayesianNetwork();

		System.out.print(bn.realizarPredicao("BaixaProporcao", "MuitoConfiavel", "CoberturaAlta", "Alta", "Alta",
				"Alto", "Medio", "Media").length);
	}

	public double[] realizarPredicao(String alteracoesRequisito, String nivelDesenvolvedores,
			String porctCobertTestesAnterior, String nivelImporStakeholderProjeto,
			String nivelImportanciaStakeholderRequisito, String custoProjeto, String deadlineProjeto,
			String notaPrioridadeRequisito) {

		IBayesInferer inferer = new JunctionTreeAlgorithm();
		inferer.setNetwork(rede);

		Map<BayesNode, String> evidence = new HashMap<BayesNode, String>();
		evidence.put(this.alteracoesRequisito, alteracoesRequisito);
		evidence.put(mediaNivelDesenvolvedor, nivelDesenvolvedores);
		evidence.put(porctCoberturaTestesAnteriores, porctCobertTestesAnterior);
		evidence.put(nivelImportStakeholderProjeto, nivelImporStakeholderProjeto);
		evidence.put(nivelImportStakeholderRequisito, nivelImportanciaStakeholderRequisito);
		evidence.put(this.custoProjeto, custoProjeto);
		evidence.put(this.deadlineProjeto, deadlineProjeto);
		evidence.put(this.notaPrioridadeRequisito, notaPrioridadeRequisito);

		inferer.setEvidence(evidence);
		double[] beliefsC = inferer.getBeliefs(predicaoFinalRequisito);

		return beliefsC;
	}

	private void iniciarNoChancesImpactoRequisito() {

		mediaNivelDesenvolvedor = rede.createNode("mediaNivelDesenvolvedor");
		mediaNivelDesenvolvedor.addOutcomes("PoucoConfiavel", "Confiavel", "MuitoConfiavel");
		mediaNivelDesenvolvedor.setProbabilities(0.33, 0.33, 0.33);

		alteracoesRequisito = rede.createNode("alteracoesRequisito");
		alteracoesRequisito.addOutcomes("AltaProporcao", "MediaProporcao", "BaixaProporcao");
		alteracoesRequisito.setProbabilities(0.33, 0.33, 0.33);

		chanceImpactoRequisito = rede.createNode("chanceImpactoRequisito");
		chanceImpactoRequisito.addOutcomes("AltoImpacto", "MedioImpacto", "BaixoImpacto");
		chanceImpactoRequisito.setParents(Arrays.asList(mediaNivelDesenvolvedor, alteracoesRequisito));

		chanceImpactoRequisito.setProbabilities(//
				0.9, 0.07, 0.03, //
				0.6, 0.3, 0.1, //
				0.25, 0.4, 0.35, //
				0.7, 0.3, 0.1, //
				0.37, 0.50, 0.13, //
				0.15, 0.25, 0.6, //
				0.6, 0.3, 0.1, //
				0.1, 0.4, 0.5, //
				0.1, 0.15, 0.75//
		);
	}

	private void iniciarNoRiscoDeBugsNoRequisito() {
		porctCoberturaTestesAnteriores = rede.createNode("porctCoberturaTestesAnteriores");
		porctCoberturaTestesAnteriores.addOutcomes("CoberturaMinima", "CoberturaMedia", "CoberturaAlta");
		porctCoberturaTestesAnteriores.setProbabilities(0.33, 0.33, 0.33);

		riscoDeBugRequisito = rede.createNode("riscoDeBugRequisito");
		riscoDeBugRequisito.addOutcomes("AltoImpacto", "MedioImpacto", "BaixoImpacto");
		riscoDeBugRequisito.setParents(Arrays.asList(porctCoberturaTestesAnteriores, chanceImpactoRequisito));

		riscoDeBugRequisito.setProbabilities(//
				0.85, 0.10, 0.05, //
				0.50, 0.45, 0.05, //
				0.40, 0.45, 0.15, //
				0.73, 0.22, 0.05, //
				0.35, 0.50, 0.15, //
				0.05, 0.40, 0.55, //
				0.65, 0.25, 0.10, //
				0.30, 0.35, 0.35, //
				0.05, 0.10, 0.85//
		);
	}

	private void iniciarNoNivelHierarquiaRequisito() {
		nivelImportStakeholderProjeto = rede.createNode("nivelImportStakeholderProjeto");
		nivelImportStakeholderProjeto.addOutcomes("Alta", "Media", "Baixa");
		nivelImportStakeholderProjeto.setProbabilities(0.33, 0.33, 0.33);

		nivelImportStakeholderRequisito = rede.createNode("nivelImportStakeholderRequisito");
		nivelImportStakeholderRequisito.addOutcomes("Alta", "Media", "Baixa");
		nivelImportStakeholderRequisito.setProbabilities(0.33, 0.33, 0.33);

		nivelHierarquiaRequisito = rede.createNode("nivelHierarquiaRequisito");
		nivelHierarquiaRequisito.addOutcomes("Alto", "Medio", "Baixo");
		nivelHierarquiaRequisito.setParents(Arrays.asList(nivelImportStakeholderProjeto, nivelHierarquiaRequisito));

		nivelHierarquiaRequisito.setProbabilities(//
				0.85, 0.15, 0.00, //
				0.55, 0.30, 0.15, //
				0.30, 0.40, 0.30, //
				0.55, 0.30, 0.15, //
				0.23, 0.50, 0.27, //
				0.05, 0.40, 0.55, //
				0.35, 0.40, 0.25, //
				0.05, 0.40, 0.55, //
				0.00, 0.15, 0.85//
		);
	}

	private void iniciarNoPrioridadeDoRequisito() {
		custoProjeto = rede.createNode("custoProjeto");
		custoProjeto.addOutcomes("Alto", "Medio", "Baixo");
		custoProjeto.setProbabilities(0.33, 0.33, 0.33);

		deadlineProjeto = rede.createNode("deadlineProjeto");
		deadlineProjeto.addOutcomes("Curto", "Medio", "Longo");
		deadlineProjeto.setProbabilities(0.33, 0.33, 0.33);

		prioridadeDoRequisito = rede.createNode("prioridadeDoRequisito");
		prioridadeDoRequisito.addOutcomes("Alta", "Media", "Baixa");

		prioridadeDoRequisito.setParents(Arrays.asList(deadlineProjeto, custoProjeto, nivelHierarquiaRequisito));

		prioridadeDoRequisito.setProbabilities(//
				0.95, 0.05, 0.00, //
				0.85, 0.10, 0.05, //
				0.70, 0.20, 0.10, //
				0.65, 0.25, 0.10, //
				0.40, 0.50, 0.10, //
				0.35, 0.35, 0.30, //
				0.55, 0.35, 0.10, //
				0.30, 0.40, 0.30, //
				0.35, 0.15, 0.50, //
				0.60, 0.35, 0.05, //
				0.40, 0.55, 0.05, //
				0.33, 0.34, 0.33, //
				0.30, 0.65, 0.05, //
				0.25, 0.50, 0.25, //
				0.20, 0.50, 0.30, //
				0.30, 0.40, 0.30, //
				0.20, 0.45, 0.35, //
				0.10, 0.30, 0.60, //
				0.55, 0.15, 0.30, //
				0.30, 0.30, 0.40, //
				0.35, 0.15, 0.50, //
				0.30, 0.33, 0.37, //
				0.15, 0.50, 0.35, //
				0.15, 0.35, 0.50, //
				0.25, 0.20, 0.55, //
				0.05, 0.30, 0.65, //
				0.00, 0.05, 0.95//
		);
	}

	private void iniciarNoPrioridadeTesteDoRequisito() {
		prioridadeDeTesteDoRequisito = rede.createNode("prioridadeDeTesteDoRequisito");
		prioridadeDeTesteDoRequisito.addOutcomes("Alta", "Media", "Baixa");

		prioridadeDeTesteDoRequisito.setParents(Arrays.asList(prioridadeDoRequisito, riscoDeBugRequisito));

		prioridadeDeTesteDoRequisito.setProbabilities(//
				0.90, 0.05, 0.05,//
				0.60, 0.35, 0.05,//
				0.45, 0.20, 0.35,//
				0.55, 0.40, 0.05,//
				0.35, 0.45, 0.20,//
				0.25, 0.40, 0.35,//
				0.30, 0.20, 0.50,//
				0.05, 0.40, 0.55,//
				0.05, 0.05, 0.90//
		);
	}

	private void iniciarNoNotaPrioridadeReuqisito() {

		// TODO
		notaPrioridadeRequisito = rede.createNode("notaPrioridadeRequisito");
		notaPrioridadeRequisito.addOutcomes("Alta", "Media", "Baixa");

		notaPrioridadeRequisito.setProbabilities(//
				0.33, 0.33, 0.33// deadlineProjeto.setProbabilities);
		);
	}

	private void predicaoFinalRequisito() {
		predicaoFinalRequisito = rede.createNode("predicaoFinalRequisito");
		predicaoFinalRequisito.addOutcomes("Alta", "Media", "Baixa");

		predicaoFinalRequisito.setParents(Arrays.asList(notaPrioridadeRequisito, prioridadeDeTesteDoRequisito));

		predicaoFinalRequisito.setProbabilities(//
				0.90, 0.05, 0.05,//
				0.60, 0.35, 0.05,//
				0.40, 0.20, 0.40,//
				0.70, 0.20, 0.10,//
				0.05, 0.90, 0.05,//
				0.05, 0.55, 0.40,//
				0.40, 0.20, 0.40,//
				0.25, 0.30, 0.45,//
				0.05, 0.05, 0.90//
				);
	}

}
