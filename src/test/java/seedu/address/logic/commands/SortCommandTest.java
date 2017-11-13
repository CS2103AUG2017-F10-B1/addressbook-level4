package seedu.address.logic.commands;

import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showFirstPersonOnly;
import static seedu.address.logic.parser.ParserUtil.Option;
import static seedu.address.testutil.TypicalPersons.getTypicalSortedAddressAddressBook;
import static seedu.address.testutil.TypicalPersons.getTypicalSortedEmailAddressBook;
import static seedu.address.testutil.TypicalPersons.getTypicalSortedNameAddressBook;
import static seedu.address.testutil.TypicalPersons.getTypicalSortedPhoneAddressBook;
import static seedu.address.testutil.TypicalPersons.getTypicalSortedRemarkAddressBook;
import static seedu.address.testutil.TypicalPersons.getUnsortedAddressAddressBook;
import static seedu.address.testutil.TypicalPersons.getUnsortedEmailAddressBook;
import static seedu.address.testutil.TypicalPersons.getUnsortedNameAddressBook;
import static seedu.address.testutil.TypicalPersons.getUnsortedPhoneAddressBook;
import static seedu.address.testutil.TypicalPersons.getUnsortedRemarkAddressBook;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.testutil.StorageStub;
//@@author TanYikai
/**
 * Contains integration tests (interaction with the Model) and unit tests for SortCommand.
 */
public class SortCommandTest {
    private static final Option sortOptionName = Option.NAME;
    private static final Option sortOptionPhone = Option.PHONE;
    private static final Option sortOptionEmail = Option.EMAIL;
    private static final Option sortOptionAddress = Option.ADDRESS;
    private static final Option sortOptionRemark = Option.REMARK;

    private Model modelName;
    private Model modelPhone;
    private Model modelEmail;
    private Model modelAddress;
    private Model modelRemark;

    private Model expectedModelName;
    private Model expectedModelPhone;
    private Model expectedModelEmail;
    private Model expectedModelAddress;
    private Model expectedModelRemark;

    private Model unsortedNameModel;
    private Model unsortedPhoneModel;
    private Model unsortedEmailModel;
    private Model unsortedAddressModel;
    private Model unsortedRemarkModel;

    private SortCommand sortCommandName;
    private SortCommand sortCommandPhone;
    private SortCommand sortCommandEmail;
    private SortCommand sortCommandAddress;
    private SortCommand sortCommandRemark;

    @Before
    public void setUp() {
        //For Name Sort
        modelName = new ModelManager(getTypicalSortedNameAddressBook(), new UserPrefs());
        expectedModelName = new ModelManager(modelName.getAddressBook(), new UserPrefs());
        unsortedNameModel = new ModelManager(getUnsortedNameAddressBook(), new UserPrefs());

        sortCommandName = new SortCommand(sortOptionName);
        sortCommandName.setData(modelName, new CommandHistory(), new UndoRedoStack(), new StorageStub());

        //For Phone Sort
        modelPhone = new ModelManager(getTypicalSortedPhoneAddressBook(), new UserPrefs());
        expectedModelPhone = new ModelManager(modelPhone.getAddressBook(), new UserPrefs());
        unsortedPhoneModel = new ModelManager(getUnsortedPhoneAddressBook(), new UserPrefs());

        sortCommandPhone = new SortCommand(sortOptionPhone);
        sortCommandPhone.setData(modelPhone, new CommandHistory(), new UndoRedoStack(), new StorageStub());

        //For Email Sort
        modelEmail = new ModelManager(getTypicalSortedEmailAddressBook(), new UserPrefs());
        expectedModelEmail = new ModelManager(modelEmail.getAddressBook(), new UserPrefs());
        unsortedEmailModel = new ModelManager(getUnsortedEmailAddressBook(), new UserPrefs());

        sortCommandEmail = new SortCommand(sortOptionEmail);
        sortCommandEmail.setData(modelEmail, new CommandHistory(), new UndoRedoStack(), new StorageStub());

        //For Address Sort
        modelAddress = new ModelManager(getTypicalSortedAddressAddressBook(), new UserPrefs());
        expectedModelAddress = new ModelManager(modelAddress.getAddressBook(), new UserPrefs());
        unsortedAddressModel = new ModelManager(getUnsortedAddressAddressBook(), new UserPrefs());

        sortCommandAddress = new SortCommand(sortOptionAddress);
        sortCommandAddress.setData(modelAddress, new CommandHistory(), new UndoRedoStack(), new StorageStub());

        //For Remark Sort
        modelRemark = new ModelManager(getTypicalSortedRemarkAddressBook(), new UserPrefs());
        expectedModelRemark = new ModelManager(modelRemark.getAddressBook(), new UserPrefs());
        unsortedRemarkModel = new ModelManager(getUnsortedRemarkAddressBook(), new UserPrefs());

        sortCommandRemark = new SortCommand(sortOptionRemark);
        sortCommandRemark.setData(modelRemark, new CommandHistory(), new UndoRedoStack(), new StorageStub());
    }

    @Test
    public void execute_sortListIsNotFiltered_showsSameSortList() {
        //For Name Sort
        assertCommandSuccess(sortCommandName, modelName, SortCommand.MESSAGE_SUCCESS, expectedModelName);

        //For Phone Sort
        assertCommandSuccess(sortCommandPhone, modelPhone, SortCommand.MESSAGE_SUCCESS, expectedModelPhone);

        //For Email Sort
        assertCommandSuccess(sortCommandEmail, modelEmail, SortCommand.MESSAGE_SUCCESS, expectedModelEmail);

        //For Address Sort
        assertCommandSuccess(sortCommandAddress, modelAddress, SortCommand.MESSAGE_SUCCESS, expectedModelAddress);

        //For Remark Sort
        assertCommandSuccess(sortCommandRemark, modelRemark, SortCommand.MESSAGE_SUCCESS, expectedModelRemark);
    }

    @Test
    public void execute_sortListIsFiltered_showsEverything() {
        //For Name Sort
        showFirstPersonOnly(modelName);
        assertCommandSuccess(sortCommandName, modelName, SortCommand.MESSAGE_SUCCESS, expectedModelName);

        //For Phone Sort
        showFirstPersonOnly(modelPhone);
        assertCommandSuccess(sortCommandPhone, modelPhone, SortCommand.MESSAGE_SUCCESS, expectedModelPhone);

        //For Email Sort
        showFirstPersonOnly(modelEmail);
        assertCommandSuccess(sortCommandEmail, modelEmail, SortCommand.MESSAGE_SUCCESS, expectedModelEmail);

        //For Address Sort
        showFirstPersonOnly(modelAddress);
        assertCommandSuccess(sortCommandAddress, modelAddress, SortCommand.MESSAGE_SUCCESS, expectedModelAddress);

        //For Remark Sort
        showFirstPersonOnly(modelRemark);
        assertCommandSuccess(sortCommandRemark, modelRemark, SortCommand.MESSAGE_SUCCESS, expectedModelRemark);
    }

    @Test
    public void equals() {
        // sort by name success -> returns true
        unsortedNameModel.sortPersons(sortOptionName);
        assertTrue(expectedModelName.equals(unsortedNameModel));

        // sort by phone success -> returns true
        unsortedPhoneModel.sortPersons(sortOptionPhone);
        assertTrue(expectedModelPhone.equals(unsortedPhoneModel));

        // sort by email success -> returns true
        unsortedEmailModel.sortPersons(sortOptionEmail);
        assertTrue(expectedModelEmail.equals(unsortedEmailModel));

        // sort by address success -> returns true
        unsortedAddressModel.sortPersons(sortOptionAddress);
        assertTrue(expectedModelAddress.equals(unsortedAddressModel));

        // sort by remark success -> returns true
        unsortedRemarkModel.sortPersons(sortOptionRemark);
        assertTrue(expectedModelRemark.equals(unsortedRemarkModel));
    }
}
