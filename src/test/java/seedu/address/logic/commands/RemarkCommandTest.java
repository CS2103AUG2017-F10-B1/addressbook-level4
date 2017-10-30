package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARK_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARK_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showFirstPersonOnly;
import static seedu.address.testutil.TypicalPersons.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.INDEX_THIRD_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.Remark;
import seedu.address.testutil.StorageStub;

/**
 * Contains integration tests (interaction with the Model) and unit tests for RemarkCommand.
 */
public class RemarkCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    /*
    @Test
    public void execute_addRemark_success() throws Exception {
        Person editedPerson = new PersonBuilder(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()))
                .withRemark("Some remark").build();
       //Person editedPerson2 = new PersonBuilder(model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased()))
        //        .withRemark("Some remark").build();

        RemarkCommand remarkCommand = prepareCommand(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON,
                editedPerson.getRemark().value);

        String expectedMessage = String.format(RemarkCommand.MESSAGE_ADD_REMARK_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);
        //Model expectedModel2 = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        //expectedModel2.updatePerson(model.getFilteredPersonList().get(1), editedPerson2);

        assertCommandSuccess(remarkCommand, model, expectedMessage, expectedModel);
        //assertCommandSuccess(remarkCommand, model, expectedMessage, expectedModel2);
    }
    */
    @Test
    public void execute_deleteRemark_success() throws Exception {
        Person editedPerson = new Person(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()));
        editedPerson.setRemark(new Remark(""));
        Person editedPerson2 = new Person(model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased()));
        editedPerson.setRemark(new Remark(""));

        RemarkCommand remarkCommand = prepareCommand(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON,
                editedPerson.getRemark().toString());

        String expectedMessage = String.format(RemarkCommand.MESSAGE_DELETE_REMARK_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);
        Model expectedModel2 = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel2.updatePerson(model.getFilteredPersonList().get(1), editedPerson2);

        assertCommandSuccess(remarkCommand, model, expectedMessage, expectedModel);
        assertCommandSuccess(remarkCommand, model, expectedMessage, expectedModel2);
    }
    /*
    @Test
    public void execute_filteredList_success() throws Exception {
        showFirstPersonOnly(model);

        ReadOnlyPerson personInFilteredList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(personInFilteredList)
                .withRemark("Some remark").build();

        RemarkCommand remarkCommand = prepareCommand(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON,
                editedPerson.getRemark().value);

        String expectedMessage = String.format(RemarkCommand.MESSAGE_ADD_REMARK_SUCCESS, editedPerson);


        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(remarkCommand, model, expectedMessage, expectedModel);

        showSecondPersonOnly(model);
        ReadOnlyPerson personInFilteredList2 = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        Person editedPerson2 = new PersonBuilder(personInFilteredList2)
                .withRemark("Some remark").build();

        String expectedMessage2 = String.format(RemarkCommand.MESSAGE_ADD_REMARK_SUCCESS, editedPerson2);


        Model expectedModel2 = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel2.updatePerson(model.getFilteredPersonList().get(1), editedPerson2);


        assertCommandSuccess(remarkCommand, model, expectedMessage2, expectedModel2);

    }
    */
    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        Index withinBoundIndex = INDEX_FIRST_PERSON;

        //One out of bound index for first index
        RemarkCommand remarkCommand = prepareCommand(outOfBoundIndex, withinBoundIndex, VALID_REMARK_BOB);

        assertCommandFailure(remarkCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        //One out of bound index for second index
        RemarkCommand remarkCommand2 = prepareCommand(withinBoundIndex, outOfBoundIndex, VALID_REMARK_BOB);

        assertCommandFailure(remarkCommand2, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        //Two out of bound index
        RemarkCommand remarkCommand3 = prepareCommand(outOfBoundIndex, outOfBoundIndex, VALID_REMARK_BOB);

        assertCommandFailure(remarkCommand3, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() throws Exception {
        showFirstPersonOnly(model);
        Index withinBoundIndex = INDEX_FIRST_PERSON;
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        //one out of bound Index for the first index
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        RemarkCommand remarkCommand = prepareCommand(outOfBoundIndex, withinBoundIndex, VALID_REMARK_BOB);

        assertCommandFailure(remarkCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        //one out of bound Index for the first index
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        RemarkCommand remarkCommand2 = prepareCommand(withinBoundIndex, outOfBoundIndex, VALID_REMARK_BOB);

        assertCommandFailure(remarkCommand2, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        //two out of bound Index
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        RemarkCommand remarkCommand3 = prepareCommand(outOfBoundIndex, outOfBoundIndex, VALID_REMARK_BOB);

        assertCommandFailure(remarkCommand3, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final RemarkCommand standardCommand = new RemarkCommand(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON,
                new Remark(VALID_REMARK_AMY));

        // same values -> returns true
        RemarkCommand commandWithSameValues = new RemarkCommand(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON,
                new Remark(VALID_REMARK_AMY));
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new RemarkCommand(INDEX_SECOND_PERSON, INDEX_THIRD_PERSON,
                new Remark(VALID_REMARK_AMY))));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new RemarkCommand(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON,
                new Remark(VALID_REMARK_BOB))));
    }

    /**
     * Returns an {@code RemarkCommand} with parameters {@code index}, {@code index2} and {@code remark}
     */
    private RemarkCommand prepareCommand(Index index, Index index2, String remark) {
        RemarkCommand remarkCommand = new RemarkCommand(index, index2, new Remark(remark));
        remarkCommand.setData(model, new CommandHistory(), new UndoRedoStack(), new StorageStub());
        return remarkCommand;
    }
}
