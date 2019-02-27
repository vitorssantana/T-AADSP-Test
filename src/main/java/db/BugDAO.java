package db;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import model.Bug;

public class BugDAO {

	private XlsDAO xlsDAO;
	private XSSFWorkbook workbook;
	private XSSFSheet abaBug;

	public BugDAO() throws IOException {
		xlsDAO = new XlsDAO();
		workbook = xlsDAO.getWorkbook();
		abaBug = workbook.getSheet("Bugs");
	}

	public List<Bug> retornarListaBugs() {

		List<Bug> listaBugs = new ArrayList<Bug>();
		Iterator<Row> iterator = abaBug.iterator();

		Row nextRow;
		if (abaBug.getRow(1) != null) {
			while (iterator.hasNext()) {
				nextRow = iterator.next();
				if (nextRow.getRowNum() == 0)
					nextRow = iterator.next();

				Iterator<Cell> cellIterator = nextRow.cellIterator();
				cellIterator = nextRow.cellIterator();
				Bug bug = new Bug();

				while (cellIterator.hasNext()) {
					Cell nextCell = cellIterator.next();
					int columnIndex = nextCell.getColumnIndex();

					switch (columnIndex) {

					case 0:
						bug.setId((int) nextCell.getNumericCellValue());
						break;

					case 1:
						bug.setTitulo(nextCell.getStringCellValue());
						break;

					case 2:
						bug.setDescricao(nextCell.getStringCellValue());
						break;

					case 3:
						bug.setIdDesenvolvedor((int) nextCell.getNumericCellValue());
						break;

					case 4:
						bug.setPrioridade(nextCell.getStringCellValue());
						break;

					case 5:
						bug.setNivelImpacto(nextCell.getStringCellValue());
						break;

					case 6:
						bug.setIdRelease((int) nextCell.getNumericCellValue());
						break;

					case 7:
						bug.setIdTarefaGeradora((int) nextCell.getNumericCellValue());
						break;

					case 8:
						bug.setIdBugGerador((int) nextCell.getNumericCellValue());
						break;
					}

				}
				listaBugs.add(bug);
			}
		}

		return listaBugs;
	}

	public void addNewBug(Bug bug) {
		Iterator<Row> iterator = abaBug.iterator();
		int count = 0;

		while (abaBug.getRow(count) != null) {
			count++;
		}

		Row row = abaBug.createRow(count);

		Cell cell = row.createCell(0);
		cell.setCellValue(count);

		cell = row.createCell(1);
		cell.setCellValue(bug.getTitulo());

		cell = row.createCell(2);
		cell.setCellValue(bug.getDescricao());

		cell = row.createCell(3);
		cell.setCellValue(bug.getIdDesenvolvedor());

		cell = row.createCell(4);
		cell.setCellValue(bug.getPrioridade());

		cell = row.createCell(5);
		cell.setCellValue(bug.getNivelImpacto());

		cell = row.createCell(6);
		// cell.setCellValue(bug.getIdRelease());
		if (bug.getIdTarefaGeradora() != null) {
			cell = row.createCell(7);
			cell.setCellValue(bug.getIdTarefaGeradora());
		}

		if (bug.getIdBugGerador() != null) {
			cell = row.createCell(8);
			cell.setCellValue(bug.getIdBugGerador());
		}

		xlsDAO.writeAndCloseXls();
	}

	public List<Bug> retornarListaBugsDisponiveis() {
		List<Bug> bugsDisponiveis = new ArrayList<Bug>();
		List<Bug> listaBugs = retornarListaBugs();

		for (Bug bug : listaBugs) {
			if (bug.getIdRelease() == null || bug.getIdRelease().equals(0)) {
				bugsDisponiveis.add(bug);
			}
		}

		return bugsDisponiveis;
	}







}
