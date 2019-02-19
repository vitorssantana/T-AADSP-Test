package db;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class XlsDAO {

	private final static String PATH = "AADSP-DB.xlsx";
	private XSSFWorkbook workbook;
	private XSSFRow row;
	private XSSFCell cell;

	public XlsDAO() throws IOException {

		File file = new File(PATH);
		if (!file.exists()) {
			gerarPlanilha();
		}
	}

	public void readXls() throws IOException {
		FileInputStream inputStream = new FileInputStream(new File(PATH));
		workbook = new XSSFWorkbook(inputStream);
	}

	public XSSFWorkbook getWorkbook() throws IOException {
		if (workbook == null)
			readXls();

		return workbook;
	}

	public void gerarPlanilha() {
		workbook = new XSSFWorkbook();
		XSSFSheet abaProjeto = workbook.createSheet("Projeto");
		XSSFSheet abaStakeholder = workbook.createSheet("Stakeholder");
		XSSFSheet abaDesenvolvedor = workbook.createSheet("Desenvolvedor");
		XSSFSheet abaRequisitos = workbook.createSheet("Requisito");
		XSSFSheet abaBugs = workbook.createSheet("Bugs");
		XSSFSheet abaSprint = workbook.createSheet("Sprint");
		XSSFSheet abaPlanoTeste = workbook.createSheet("PlanoTeste");

		preencherCabecalhoAbaProjeto(abaProjeto);
		preencherCabecalhoAbaStakeholder(abaStakeholder);
		preencherCabecalhoAbaDesenvolvedor(abaDesenvolvedor);
		preencherCabecalhoAbaRequisitos(abaRequisitos);
		preencherCabecalhoAbaBugs(abaBugs);
		preencherCabecalhoAbaSprint(abaSprint);
		preencherCabecalhoAbaPlanoTeste(abaPlanoTeste);
		writeAndCloseXls();
		System.out.println("Arquivo Excel criado com sucesso!");

	}

	private void preencherCabecalhoAbaProjeto(XSSFSheet abaProjeto) {

		row = abaProjeto.createRow(0);

		cell = row.createCell(0);
		cell.setCellValue("ID");
		cell.setCellStyle(estilizarCabecalho(workbook));

		cell = row.createCell(1);
		cell.setCellValue("Nome");
		cell.setCellStyle(estilizarCabecalho(workbook));

		cell = row.createCell(2);
		cell.setCellValue("Nível de Prioridade");
		cell.setCellStyle(estilizarCabecalho(workbook));

		cell = row.createCell(2);
		cell.setCellValue("Id_Stakeholder");
		cell.setCellStyle(estilizarCabecalho(workbook));

		cell = row.createCell(3);
		cell.setCellValue("Custo");
		cell.setCellStyle(estilizarCabecalho(workbook));

		cell = row.createCell(4);
		cell.setCellValue("Prazo de Entrega");
		cell.setCellStyle(estilizarCabecalho(workbook));
	}

	private void preencherCabecalhoAbaStakeholder(XSSFSheet abaStakeholder) {

		row = abaStakeholder.createRow(0);

		cell = row.createCell(0);
		cell.setCellValue("ID");
		cell.setCellStyle(estilizarCabecalho(workbook));

		cell = row.createCell(1);
		cell.setCellValue("Nome");
		cell.setCellStyle(estilizarCabecalho(workbook));

		cell = row.createCell(2);
		cell.setCellValue("Nível de Importancia");
		cell.setCellStyle(estilizarCabecalho(workbook));
	}

	private void preencherCabecalhoAbaDesenvolvedor(XSSFSheet abaDesenvolvedor) {

		row = abaDesenvolvedor.createRow(0);

		cell = row.createCell(0);
		cell.setCellValue("ID");
		cell.setCellStyle(estilizarCabecalho(workbook));

		cell = row.createCell(1);
		cell.setCellValue("Nome");
		cell.setCellStyle(estilizarCabecalho(workbook));

		cell = row.createCell(2);
		cell.setCellValue("Nivel de Experiência");
		cell.setCellStyle(estilizarCabecalho(workbook));
	}

	private void preencherCabecalhoAbaPlanoTeste(XSSFSheet abaPlanoTeste) {

		row = abaPlanoTeste.createRow(0);

		cell = row.createCell(0);
		cell.setCellValue("ID");
		cell.setCellStyle(estilizarCabecalho(workbook));

		cell = row.createCell(1);
		cell.setCellValue("Descricao");
		cell.setCellStyle(estilizarCabecalho(workbook));

		cell = row.createCell(2);
		cell.setCellValue("Tipo_Teste");
		cell.setCellStyle(estilizarCabecalho(workbook));

		cell = row.createCell(3);
		cell.setCellValue("ID_Requisito");
		cell.setCellStyle(estilizarCabecalho(workbook));
	}

	private void preencherCabecalhoAbaRequisitos(XSSFSheet abaRequisitos) {

		row = abaRequisitos.createRow(0);

		cell = row.createCell(0);
		cell.setCellValue("ID");
		cell.setCellStyle(estilizarCabecalho(workbook));

		cell = row.createCell(1);
		cell.setCellValue("Titulo");
		cell.setCellStyle(estilizarCabecalho(workbook));

		cell = row.createCell(2);
		cell.setCellValue("Id_Projeto");
		cell.setCellStyle(estilizarCabecalho(workbook));

		cell = row.createCell(3);
		cell.setCellValue("Nota_Prioridade");
		cell.setCellStyle(estilizarCabecalho(workbook));

		cell = row.createCell(4);
		cell.setCellValue("Id_Stakeholder");
		cell.setCellStyle(estilizarCabecalho(workbook));
	}

	private void preencherCabecalhoAbaBugs(XSSFSheet abaBugs) {

		row = abaBugs.createRow(0);

		cell = row.createCell(0);
		cell.setCellValue("ID");
		cell.setCellStyle(estilizarCabecalho(workbook));

		cell = row.createCell(1);
		cell.setCellValue("Título");
		cell.setCellStyle(estilizarCabecalho(workbook));

		cell = row.createCell(2);
		cell.setCellValue("Status");
		cell.setCellStyle(estilizarCabecalho(workbook));

		cell = row.createCell(3);
		cell.setCellValue("Id_Desenvolvedor");
		cell.setCellStyle(estilizarCabecalho(workbook));

		cell = row.createCell(4);
		cell.setCellValue("Prioridade");
		cell.setCellStyle(estilizarCabecalho(workbook));

		cell = row.createCell(5);
		cell.setCellValue("Nivel_Impacto");
		cell.setCellStyle(estilizarCabecalho(workbook));

		cell = row.createCell(6);
		cell.setCellValue("Id_Sprint");
		cell.setCellStyle(estilizarCabecalho(workbook));
		
		cell = row.createCell(7);
		cell.setCellValue("Id_Requisito");
		cell.setCellStyle(estilizarCabecalho(workbook));
	}

	private void preencherCabecalhoAbaSprint(XSSFSheet abaSprint) {

		row = abaSprint.createRow(0);

		cell = row.createCell(0);
		cell.setCellValue("ID");
		cell.setCellStyle(estilizarCabecalho(workbook));

		cell = row.createCell(1);
		cell.setCellValue("Versao");
		cell.setCellStyle(estilizarCabecalho(workbook));

		cell = row.createCell(2);
		cell.setCellValue("Data Inicio");
		cell.setCellStyle(estilizarCabecalho(workbook));

		cell = row.createCell(3);
		cell.setCellValue("Data Fim");
		cell.setCellStyle(estilizarCabecalho(workbook));
		

		cell = row.createCell(4);
		cell.setCellValue("Status");
		cell.setCellStyle(estilizarCabecalho(workbook));
	}

	public void writeAndCloseXls() {
		try {
			FileOutputStream out;
			out = new FileOutputStream(new File(PATH));
			workbook.write(out);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("deprecation")
	private CellStyle estilizarCabecalho(XSSFWorkbook workbook) {
		CellStyle style = workbook.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setFillForegroundColor(HSSFColor.LEMON_CHIFFON.index);
		style.setBorderRight(CellStyle.BORDER_THIN);
		style.setRightBorderColor(IndexedColors.BLACK.getIndex());
		style.setBorderBottom(CellStyle.BORDER_THIN);
		style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		style.setBorderLeft(CellStyle.BORDER_THIN);
		style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		style.setBorderTop(CellStyle.BORDER_THIN);
		style.setTopBorderColor(IndexedColors.BLACK.getIndex());
		return style;
	}
}
