# TanYikai
###### \java\seedu\address\logic\commands\AddCommand.java
``` java
    public static final String COMMAND_ALIAS = "a";
```
###### \java\seedu\address\logic\commands\SortCommand.java
``` java
/**
 * Lists all sorted persons in the address book to the user.
 */
public class SortCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "sort";
    public static final String COMMAND_ALIAS = "st";
    public static final String SHORT_MESSAGE_USAGE = COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Sorted all persons";

    @Override
    public CommandResult executeUndoableCommand() {
        model.sortPersons();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
```
###### \java\seedu\address\logic\parser\ParserUtil.java
``` java
    /**
     * Parses a {@code Optional<String> phone} into an {@code Optional<Phone>} if {@code phone} is present.
     * If {@code phone} is not present, {@code String <Unspecified phone number>} is given
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Phone> parseAddPhone(Optional<String> phone) throws IllegalValueException {
        requireNonNull(phone);
        return phone.isPresent() ? Optional.of(new Phone(phone.get())) : Optional.of(Phone.UNSPECIFIED);
    }
```
###### \java\seedu\address\logic\parser\ParserUtil.java
``` java
    /**
     * Parses a {@code Optional<String> address} into an {@code Optional<Address>} if {@code address} is present.
     * If {@code address} is not present, {@code String <Unspecified address>} is given
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Address> parseAddAddress(Optional<String> address) throws IllegalValueException {
        requireNonNull(address);
        return address.isPresent() ? Optional.of(new Address(address.get())) : Optional.of(Address.UNSPECIFIED);
    }
```
###### \java\seedu\address\logic\parser\ParserUtil.java
``` java
    /**
     * Parses a {@code Optional<String> email} into an {@code Optional<Email>} if {@code email} is present.
     * If {@code email} is not present, {@code String <Unspecified email>} is given
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Email> parseAddEmail(Optional<String> email) throws IllegalValueException {
        requireNonNull(email);
        return email.isPresent() ? Optional.of(new Email(email.get())) : Optional.of(Email.UNSPECIFIED);
    }
```
###### \java\seedu\address\logic\parser\RemarkCommandParser.java
``` java
/**
 * Parses input arguments and creates a new RemarkCommand object
 */
public class RemarkCommandParser implements Parser<RemarkCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the RemarkCommand
     * and returns an RemarkCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RemarkCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_OTHER_INDEX, PREFIX_REMARK);

        Index index;
        Index index2;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
            index2 = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_OTHER_INDEX).orElse("NO Index"));
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemarkCommand.MESSAGE_USAGE));
        }

        String remark = argMultimap.getValue(PREFIX_REMARK).orElse("");

        return new RemarkCommand(index, index2, new Remark(remark));
    }
}

```
###### \java\seedu\address\model\person\Address.java
``` java
    /**
     * The default Address constructor when address is not specified by the user
     */
    private Address() {
        value = "Unspecified address";
    }
```
###### \java\seedu\address\model\person\Email.java
``` java
    /**
     * The default Email constructor when email is not specified by the user
     */
    private Email() {
        value = "Unspecified email";
    }
```
###### \java\seedu\address\model\person\Phone.java
``` java
    /**
     * The default Phone constructor when phone is not specified by the user
     */
    private Phone() {
        value = "Unspecified phone number";
    }
```
###### \java\seedu\address\model\person\Remark.java
``` java
/**
 * Represents a Person's remark in the address book.
 * Guarantees: immutable; is always valid
 */
public class Remark {

    public static final String MESSAGE_REMARK_CONSTRAINTS =
            "Person remarks can take any values, can even be blank";

    public final String value;

    public Remark(String remark) {
        requireNonNull(remark);
        this.value = remark;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Remark // instanceof handles nulls
                && this.value.equals(((Remark) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}

```
###### \java\seedu\address\model\person\UniquePersonList.java
``` java
    /**
     * Sorts the persons object in the list alphanumerically by name.
     */
    public void sort() {
        requireNonNull(internalList);
        Collections.sort(internalList);
    }
```
