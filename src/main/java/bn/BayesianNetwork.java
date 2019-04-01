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
	// Calcular Bug
	private BayesNode alteracoesRequisito;
	private BayesNode mediaNivelDesenvolvedor;
	private BayesNode chanceImpactoRequisito;
	private BayesNode porctCoberturaTestesAnteriores;
	private BayesNode riscoDeBugRequisito;
	// Calcular importancia
	private BayesNode notaPrioridadeRequisito;
	private BayesNode nivelImportStakeholderRequisito;
	private BayesNode importanciaRequisito;
	private BayesNode custoProjeto;
	private BayesNode deadlineProjeto;
	private BayesNode nivelImportStakeholderProjeto;
	private BayesNode importanciaProjeto;
	private BayesNode statusRequisito;
	// Predicao Final
	private BayesNode prioridadeTesteRequisito;

	public BayesianNetwork() {
		rede = new BayesNet();
		iniciarNoChancesImpactoRequisito();
		iniciarNoRiscoDeBugsNoRequisito();
		iniciarNoImportanciaRequisito();
		iniciarNoImportanciaProjeto();
		iniciarNoStatusRequisito();
		iniciarNoPrioridadeTesteDoRequisito();

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
		double[] beliefsC = inferer.getBeliefs(prioridadeTesteRequisito);

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

		chanceImpactoRequisito.setProbabilities 
		
		// AltoImpacto  MedioImpacto BaixoImpacto  // MediaNivelDesenvolvedor AlteracoesRequisito 
		  (0.9,         0.07,        0.03,         // PoucoConfiavel          AltaProporcao       
		   0.65,        0.23,        0.12,         // PoucoConfiavel          MediaProprocao      
		   0.45,        0.35,        0.2,          // PoucoConfiavel          BaixaProrpocao      
		   0.7,         0.25,        0.05,         // Confiavel               AltaProporcao       
		   0.25,        0.65,        0.1,          // Confiavel               MediaProprocao      
		   0.2,         0.35,        0.45,         // Confiavel               BaixaProrpocao      
		   0.6,         0.15,        0.25,         // MuitoConfiavel          AltaProporcao       
		   0.25,        0.3,         0.45,         // MuitoConfiavel          MediaProprocao      
		   0.05,        0.1,         0.85);        // MuitoConfiavel          BaixaProrpocao      
	}

	private void iniciarNoRiscoDeBugsNoRequisito() {
		porctCoberturaTestesAnteriores = rede.createNode("porctCoberturaTestesAnteriores");
		porctCoberturaTestesAnteriores.addOutcomes("CoberturaMinima", "CoberturaMedia", "CoberturaAlta");
		porctCoberturaTestesAnteriores.setProbabilities(0.33, 0.33, 0.33);

		riscoDeBugRequisito = rede.createNode("riscoDeBugRequisito");
		riscoDeBugRequisito.addOutcomes("AltoImpacto", "MedioImpacto", "BaixoImpacto");
		riscoDeBugRequisito.setParents(Arrays.asList(porctCoberturaTestesAnteriores, chanceImpactoRequisito));

		riscoDeBugRequisito.setProbabilities

			// Grande       Medio        Baixo         // PorctCoberExecTestesRelAnt ChanceDeImpactoNoRequisito 
		  (0.9,         0.07,        0.03,         // CoberturaMinima            AltoImpacto                
				   0.5,         0.3,         0.2,          // CoberturaMinima            MedioImpacto               
				   0.25,        0.3,         0.45,         // CoberturaMinima            BaixoImpacto               
				   0.65,        0.3,         0.05,         // CoberturaMedia             AltoImpacto                
				   0.15,        0.75,        0.1,          // CoberturaMedia             MedioImpacto               
				   0.15,        0.35,        0.5,          // CoberturaMedia             BaixoImpacto               
				   0.55,        0.2,         0.25,         // CoberturaAlta              AltoImpacto                
				   0.25,        0.4,         0.35,         // CoberturaAlta              MedioImpacto               
				   0.03,        0.07,        0.9);         // CoberturaAlta              BaixoImpacto                  ;
	}

	private void iniciarNoImportanciaRequisito() {
		notaPrioridadeRequisito = rede.createNode("notaPrioridadeRequisito");
		notaPrioridadeRequisito.addOutcomes("Alta", "Media", "Baixa");
		notaPrioridadeRequisito.setProbabilities(0.33, 0.33, 0.33);

		nivelImportStakeholderRequisito= rede.createNode("nivelImportStakeholderRequisito");
		nivelImportStakeholderRequisito.addOutcomes("Alta", "Media", "Baixa");
		nivelImportStakeholderRequisito.setProbabilities(0.33, 0.33, 0.33);
		
		importanciaRequisito = rede.createNode("importanciaRequisito");
		importanciaRequisito.addOutcomes("Alto", "Medio", "Baixo");
		importanciaRequisito.setParents(Arrays.asList(nivelImportStakeholderRequisito, notaPrioridadeRequisito));
		importanciaRequisito.setProbabilities
		
		// Alto         Medio        Baixo         // NivelImporStakeholderRequisito NotaPrioridadeRequisito 
		  (1,           0,           0,            // Alta                           Alta                    
		   0.45,        0.45,        0.1,          // Alta                           Media                   
		   0.55,        0.15,        0.3,          // Alta                           Baixa                   
		   0.5,         0.4,         0.1,          // Media                          Alta                    
		   0,           1,           0,            // Media                          Media                   
		   0.25,        0.35,        0.4,          // Media                          Baixa                   
		   0.55,        0.15,        0.3,          // Baixa                          Alta                    
		   0.25,        0.3,         0.45,         // Baixa                          Media                   
		   0,           0,           1);           // Baixa                          Baixa              
	}

	private void iniciarNoImportanciaProjeto() {
		custoProjeto = rede.createNode("custoProjeto");
		custoProjeto.addOutcomes("Alto", "Medio", "Baixo");
		custoProjeto.setProbabilities(0.33, 0.33, 0.33);
		
		deadlineProjeto = rede.createNode("deadlineProjeto");
		deadlineProjeto.addOutcomes("Curto", "Medio", "Longo");
		deadlineProjeto.setProbabilities(0.33, 0.33, 0.33);
		
		nivelImportStakeholderProjeto = rede.createNode("nivelImportStakeholderProjeto");
		nivelImportStakeholderProjeto.addOutcomes("Alta", "Media", "Baixa");
		nivelImportStakeholderProjeto.setProbabilities(0.33, 0.33, 0.33);
		
		importanciaProjeto = rede.createNode("importanciaProjeto");
		importanciaProjeto.addOutcomes("Alto", "Medio", "Baixo");
		importanciaProjeto.setParents(Arrays.asList(nivelImportStakeholderProjeto, custoProjeto,deadlineProjeto));
		importanciaProjeto.setProbabilities

		// Alto         Medio        Baixo         // NivelImportStakeholderProjeto CustoProjeto DeadlineDoProjeto 
		  (1,           0,           0,            // Alta                          Alto         Curto             
				   0.7,         0.25,        0.05,         // Alta                          Alto         Medio             
				   0.6,         0.1,         0.3,          // Alta                          Alto         Longo             
				   0.75,        0.2,         0.05,         // Alta                          Medio        Curto             
				   0.25,        0.65,        0.1,          // Alta                          Medio        Medio             
				   0.3,         0.4,         0.3,          // Alta                          Medio        Longo             
				   0.6,         0.1,         0.3,          // Alta                          Baixo        Curto             
				   0.3,         0.3,         0.4,          // Alta                          Baixo        Medio             
				   0.25,        0.05,        0.7,          // Alta                          Baixo        Longo             
				   0.68,        0.28,        0.04,         // Media                         Alto         Curto             
				   0.35,        0.6,         0.05,         // Media                         Alto         Medio             
				   0.3,         0.3,         0.4,          // Media                         Alto         Longo             
				   0.3,         0.65,        0.05,         // Media                         Medio        Curto             
				   0.1,         0.85,        0.05,         // Media                         Medio        Medio             
				   0.1,         0.6,         0.3,          // Media                         Medio        Longo             
				   0.3,         0.4,         0.3,          // Media                         Baixo        Curto             
				   0.05,        0.65,        0.3,          // Media                         Baixo        Medio             
				   0.05,        0.3,         0.65,         // Media                         Baixo        Longo             
				   0.65,        0.05,        0.3,          // Baixa                         Alto         Curto             
				   0.3,         0.4,         0.3,          // Baixa                         Alto         Medio             
				   0.25,        0.1,         0.65,         // Baixa                         Alto         Longo             
				   0.4,         0.3,         0.3,          // Baixa                         Medio        Curto             
				   0.1,         0.65,        0.25,         // Baixa                         Medio        Medio             
				   0.1,         0.25,        0.65,         // Baixa                         Medio        Longo             
				   0.25,        0.1,         0.65,         // Baixa                         Baixo        Curto             
				   0.1,         0.2,         0.7,          // Baixa                         Baixo        Medio             
				   0.05,        0.1,         0.85);        // Baixa                         Baixo        Longo
	}

	private void iniciarNoStatusRequisito() {
		statusRequisito = rede.createNode("statusRequisito");
		statusRequisito.addOutcomes("Alta", "Media", "Baixa");
		statusRequisito.setParents(Arrays.asList(importanciaRequisito, importanciaProjeto));
		statusRequisito.setProbabilities
		// Alta         Media        Baixa         // ImportanciaRequisito ImportanciaProjeto 
		  (0.95,        0.05,        0,            // Alto                 Alto               
		   0.75,        0.2,         0.05,         // Alto                 Medio              
		   0.65,        0.1,         0.25,         // Alto                 Baixo              
		   0.75,        0.2,         0.05,         // Medio                Alto               
		   0.1,         0.85,        0.05,         // Medio                Medio              
		   0.05,        0.4,         0.55,         // Medio                Baixo              
		   0.6,         0.1,         0.3,          // Baixo                Alto               
		   0.05,        0.55,        0.4,          // Baixo                Medio              
		   0,           0.05,        0.95);        // Baixo                Baixo              ;
	}

	private void iniciarNoPrioridadeTesteDoRequisito() {
		prioridadeTesteRequisito = rede.createNode("prioridadeTesteRequisito");
		prioridadeTesteRequisito.addOutcomes("Alta", "Media", "Baixa");
		prioridadeTesteRequisito.setParents(Arrays.asList(statusRequisito, riscoDeBugRequisito));
		prioridadeTesteRequisito.setProbabilities
		// Alta         Media        Baixa         // StatusRequisito RiscoDeBugsNoRequisito 
		  (0.95,        0.05,        0,            // Alta            Grande                 
		   0.65,        0.25,        0.1,          // Alta            Medio                  
		   0.4,         0.5,         0.1,          // Alta            Baixo                  
		   0.6,         0.35,        0.05,         // Media           Grande                 
		   0.1,         0.85,        0.05,         // Media           Medio                  
		   0.15,        0.35,        0.5,          // Media           Baixo                  
		   0.25,        0.25,        0.5,          // Baixa           Grande                 
		   0.05,        0.35,        0.6,          // Baixa           Medio                  
		   0,           0.05,        0.95);        // Baixa           Baixo                  ;
	}
}
