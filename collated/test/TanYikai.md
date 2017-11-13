# TanYikai
###### /java/seedu/address/logic/commands/AddCommandTest.java
``` java
        @Override
        public void sortPersons(Option sortOption) {
            fail("This method should not be called.");
        }
```
###### /java/seedu/address/logic/commands/SortCommandTest.java
``` java
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
```
###### /java/seedu/address/logic/parser/AddCommandParserTest.java
``` java
        // multiple remarks - last remarks accepted
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + REMARK_DESC_AMY + REMARK_DESC_BOB + TAG_DESC_FRIEND,
                new AddCommand(expectedPerson));
```
###### /java/seedu/address/logic/parser/AddCommandParserTest.java
``` java
        // unspecified phone prefix
        expectedPerson = new PersonBuilder().withName(VALID_NAME_AMY).withUnspecifiedPhone()
                .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY).withRemark(VALID_REMARK_AMY)
                .withTags().build();
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_AMY
                + EMAIL_DESC_AMY + ADDRESS_DESC_AMY + REMARK_DESC_AMY, new AddCommand(expectedPerson));

        // unspecified email prefix
        expectedPerson = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
                .withUnspecifiedEmail().withAddress(VALID_ADDRESS_AMY).withRemark(VALID_REMARK_AMY).withTags().build();
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY
                + ADDRESS_DESC_AMY + REMARK_DESC_AMY, new AddCommand(expectedPerson));

        //unspecified address prefix
        expectedPerson = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
                .withEmail(VALID_EMAIL_AMY).withUnspecifiedAddress().withRemark(VALID_REMARK_AMY).withTags().build();
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY
                + EMAIL_DESC_AMY + REMARK_DESC_AMY, new AddCommand(expectedPerson));

        //unspecified remark prefix
        expectedPerson = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
                .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY).withUnspecifiedRemark().withTags().build();
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY
                + EMAIL_DESC_AMY + ADDRESS_DESC_AMY, new AddCommand(expectedPerson));

        //unspecified phone, email, address and remark prefix
        expectedPerson = new PersonBuilder().withName(VALID_NAME_AMY).withUnspecifiedPhone()
                .withUnspecifiedEmail().withUnspecifiedAddress().withUnspecifiedRemark().withTags().build();
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_AMY, new AddCommand(expectedPerson));
    }
```
###### /java/seedu/address/logic/parser/AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_sort() throws Exception {
        assertTrue(parser.parseCommand(SortCommand.COMMAND_WORD + " " + PREFIX_NAME) instanceof SortCommand);
        assertTrue(parser.parseCommand(SortCommand.COMMAND_WORD + " " + PREFIX_PHONE) instanceof SortCommand);
        assertTrue(parser.parseCommand(SortCommand.COMMAND_WORD + " " + PREFIX_EMAIL) instanceof SortCommand);
        assertTrue(parser.parseCommand(SortCommand.COMMAND_WORD + " " + PREFIX_ADDRESS) instanceof SortCommand);
        assertTrue(parser.parseCommand(SortCommand.COMMAND_WORD + " " + PREFIX_REMARK) instanceof SortCommand);
    }
```
###### /java/seedu/address/logic/parser/EditCommandParserTest.java
``` java
        // remark
        userInput = targetIndex.getOneBased() + REMARK_DESC_BOB;
        descriptor = new EditPersonDescriptorBuilder().withRemark(VALID_REMARK_BOB).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
```
###### /java/seedu/address/model/person/RemarkTest.java
``` java
public class RemarkTest {

    @Test
    public void equals() {
        Remark remark = new Remark("Hello");

        // same object -> returns true
        assertTrue(remark.equals(remark));

        // same values -> returns true
        Remark remarkCopy = new Remark(remark.value);
        assertTrue(remark.equals(remarkCopy));

        // different types -> returns false
        assertFalse(remark.equals(1));

        // null -> returns false
        assertFalse(remark.equals(null));

        // different person -> returns false
        Remark differentRemark = new Remark("Bye");
        assertFalse(remark.equals(differentRemark));
    }
}
```
###### /java/seedu/address/testutil/TypicalPersons.java
``` java
    /**
     * Returns an {@code AddressBook} with all the typical persons sorted by name.
     */
    public static AddressBook getTypicalSortedNameAddressBook() {
        AddressBook ab = new AddressBook();
        for (ReadOnlyPerson person : getTypicalSortedNamePersons()) {
            try {
                ab.addPerson(person);
            } catch (DuplicatePersonException e) {
                assert false : "not possible";
            }
        }
        return ab;
    }

    /**
     * Returns an {@code AddressBook} with all the typical persons sorted by phone.
     */
    public static AddressBook getTypicalSortedPhoneAddressBook() {
        AddressBook ab = new AddressBook();
        for (ReadOnlyPerson person : getTypicalSortedPhonePersons()) {
            try {
                ab.addPerson(person);
            } catch (DuplicatePersonException e) {
                assert false : "not possible";
            }
        }
        return ab;
    }

    /**
     * Returns an {@code AddressBook} with all the typical persons sorted by email.
     */
    public static AddressBook getTypicalSortedEmailAddressBook() {
        AddressBook ab = new AddressBook();
        for (ReadOnlyPerson person : getTypicalSortedEmailPersons()) {
            try {
                ab.addPerson(person);
            } catch (DuplicatePersonException e) {
                assert false : "not possible";
            }
        }
        return ab;
    }

```
###### /java/seedu/address/testutil/TypicalPersons.java
``` java
    public static List<ReadOnlyPerson> getTypicalSortedNamePersons() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }

    public static List<ReadOnlyPerson> getTypicalSortedPhonePersons() {
        return new ArrayList<>(Arrays.asList(ALICE, DANIEL, ELLE, FIONA, GEORGE, CARL, BENSON));
    }

    public static List<ReadOnlyPerson> getTypicalSortedEmailPersons() {
        return new ArrayList<>(Arrays.asList(ALICE, GEORGE, DANIEL, CARL, BENSON, FIONA, ELLE));
    }

    public static List<ReadOnlyPerson> getTypicalSortedAddressPersons() {
        return new ArrayList<>(Arrays.asList(DANIEL, ALICE, BENSON, GEORGE, FIONA, ELLE, CARL));
    }

    public static List<ReadOnlyPerson> getTypicalSortedRemarkPersons() {
        return new ArrayList<>(Arrays.asList(ALICE, FIONA, CARL, DANIEL, ELLE, BENSON, GEORGE));
    }

    public static List<ReadOnlyPerson> getUnsortedNameTypicalPersons() {
        return new ArrayList<>(Arrays.asList(DANIEL, BENSON, CARL, ALICE, ELLE, FIONA, GEORGE));
    }

    public static List<ReadOnlyPerson> getUnsortedPhoneTypicalPersons() {
        return new ArrayList<>(Arrays.asList(ELLE, BENSON, CARL, ALICE, DANIEL, FIONA, GEORGE));
    }

    public static List<ReadOnlyPerson> getUnsortedEmailTypicalPersons() {
        return new ArrayList<>(Arrays.asList(DANIEL, BENSON, FIONA, ALICE, ELLE, CARL, GEORGE));
    }

    public static List<ReadOnlyPerson> getUnsortedAddressTypicalPersons() {
        return new ArrayList<>(Arrays.asList(DANIEL, BENSON, CARL, ALICE, ELLE, FIONA, GEORGE));
    }

    public static List<ReadOnlyPerson> getUnsortedRemarkTypicalPersons() {
        return new ArrayList<>(Arrays.asList(ALICE, FIONA, CARL, GEORGE, DANIEL, ELLE, BENSON));
    }

```
