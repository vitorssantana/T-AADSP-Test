package db;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import model.Projeto;

public class ProjetoDAO {

	private XlsDAO xlsDAO;
	private XSSFWorkbook workbook;
	private XSSFSheet abaProjeto;

	public ProjetoDAO() throws IOException {
		xlsDAO = new XlsDAO();
		workbook = xlsDAO.getWorkbook();
		abaProjeto = workbook.getSheet("Projeto");
	}

	public List<Projeto> retornarListarProjetos() {

		List<Projeto> listaProjetos = new ArrayList<Projeto>();
		Iterator<Row> iterator = abaProjeto.iterator();

		iterator.next();
		Row nextRow;

		while (iterator.hasNext()) {
			nextRow = iterator.next();
//			if (nextRow.getRowNum() == 0)
//				nextRow = iterator.next();

			Iterator<Cell> cellIterator = nextRow.cellIterator();
			cellIterator = nextRow.cellIterator();
			Projeto projeto = new Projeto();

			while (cellIterator.hasNext()) {
				Cell nextCell = cellIterator.next();
				int columnIndex = nextCell.getColumnIndex();

				switch (columnIndex) {

				case 0:
					projeto.setId((int) nextCell.getNumericCellValue());
					break;

				case 1:
					projeto.setNome(nextCell.getStringCellValue());
					break;

				case 2:
					projeto.setIdStakeholder((int) nextCell.getNumericCellValue());
					break;

				case 3:
					projeto.setCusto(nextCell.getNumericCellValue());
					break;

				case 4:
					projeto.setPrazoEntrega(nextCell.getStringCellValue());
					break;

				}

			}
			listaProjetos.add(projeto);
		}

		return listaProjetos;

	}

	public void addNewProjeto(Projeto projeto) {

		int count = 1;

		while (abaProjeto.getRow(count) != null) {
			count++;
		}
		Row row = abaProjeto.createRow(count);

		Cell cell = row.createCell(0);
		cell.setCellValue(count);

		cell = row.createCell(1);
		cell.setCellValue(projeto.getNome());

		cell = row.createCell(2);
		cell.setCellValue(projeto.getIdStakeholder());

		cell = row.createCell(3);
		cell.setCellValue(projeto.getCusto());

		cell = row.createCell(4);
		cell.setCellValue(projeto.getPrazoEntrega());

		xlsDAO.writeAndCloseXls();
	}

	public void editarDadosProjeto(Projeto projeto) {
		int count = 1;
		while ((int) abaProjeto.getRow(count).getCell(0).getNumericCellValue() != projeto.getId()) {
			count++;
		}

		Row row = abaProjeto.getRow(count);

		Cell cell = row.getCell(1);
		cell.setCellValue(projeto.getNome());

		cell = row.createCell(2);
		cell.setCellValue(projeto.getIdStakeholder());

		cell = row.createCell(3);
		cell.setCellValue(projeto.getCusto());

		cell = row.createCell(4);
		cell.setCellValue(projeto.getPrazoEntrega());

		xlsDAO.writeAndCloseXls();
	}

	public void removerProjeto(Projeto projeto) {
		int count = 1;
		while (true) {
			if (abaProjeto.getRow(count) == null) {
				count++;
			} else if ((int) abaProjeto.getRow(count).getCell(0).getNumericCellValue() != projeto.getId()) {
				count++;
			} else {
				break;
			}
		}
		Row row = abaProjeto.getRow(count);
		abaProjeto.removeRow(row);

		xlsDAO.writeAndCloseXls();
	}

}
