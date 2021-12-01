import { Eventful } from "./eventful";
import { load } from "./timing";

export type ValidatorCondition = (string: string) => Array<string>;

export function validateUscId(string: string): Array<string> {
  return /^\d{10}$/.test(string) ? [] : ["Invalid USC ID!"];
}

export function validateEmail(string: string): Array<string> {
  return /^[^@]+@[^.]+\..+$/.test(string) ? [] : ["Invalid email address!"];
}

export function validateUscEmail(string: string): Array<string> {
  return /^\w+@usc.edu/.test(string) ? [] : ["Invalid USC email address!"];
}

export function makeSameAs(id: string, name: string): ValidatorCondition {
  return (string: string) => {
    const element = document.getElementById(id) as HTMLInputElement;
    if (element === null) {
      throw new Error(`element ${id} does not exist`);
    }
    return string === element.value ? [] : [`Must match ${name}!`];
  };
}

export function makeNotEmpty(name: string): ValidatorCondition {
  return (string: string) => {
    return string.length > 0 ? [] : [`${name} must not be empty`];
  };
}

export function makeRegex(pattern: RegExp, name: string): ValidatorCondition {
  return (string: string) => {
    return pattern.test(string) ? [] : [`Invalidly formatted ${name}`];
  };
}

export abstract class BaseValidatorField extends Eventful {
  public valid = false;

  public abstract validate(): void;

  public reset(): void {
    this.valid = false;
  }

  public handle(errors: Array<string> = []): void {
    this.valid = errors.length === 0;
    this.emit("valid", errors);
  }
}

export abstract class ValidatorField extends BaseValidatorField {
  public conditions: Array<ValidatorCondition>;

  protected constructor() {
    super();
    this.conditions = [];
    this.valid = false;
  }

  public with(condition: ValidatorCondition): this {
    this.conditions.push(condition);
    return this;
  }

  public abstract read(): any;

  public validate(): void {
    const errors = [];
    for (const condition of this.conditions) {
      errors.push(...condition(this.read()));
    }
    this.handle(errors);
  }
}

export class ElementValidatorField extends ValidatorField {
  public id: string;
  public element: HTMLInputElement | null;

  public constructor(id: string, initial = false) {
    super();
    this.id = id;
    this.element = null;

    // Get element and bind once available
    load().then(() => this.bind(initial));
  }

  public read(): any {
    return this.element!.value;
  }

  public listening(id: string): this {
    load().then(() => {
      const element = document.getElementById(id);
      if (element !== null) {
        this.listen(element as HTMLInputElement);
      } else {
        console.error(`couldn't find element ${this.id}`);
      }
    });
    return this;
  }

  public listen(element: HTMLInputElement): void {
    const validate = () => this.validate();
    element.addEventListener("input", validate);
    element.addEventListener("blur", validate);
  }

  public bind(initial: boolean): void {
    this.element = document.getElementById(this.id) as HTMLInputElement | null;
    if (this.element !== null) {
      this.listen(this.element);
      if (initial) {
        this.validate();
      }
    } else {
      console.error(`couldn't find element ${this.id}`);
    }
  }

  public reset(): void {
    this.element!.classList.remove("invalid");
  }

  public validate(): void {
    super.validate();
    this.element!.classList[this.valid ? "remove" : "add"]("invalid");
  }
}

export class Validator extends Eventful {
  public initial: boolean;
  public fields: Map<string, BaseValidatorField>;
  public valid: boolean;
  public disabled: string;
  public errors: { [id: string]: string };

  public constructor(initial = false) {
    super();
    this.initial = initial;
    this.fields = new Map();
    this.valid = false;
    this.disabled = "disabled";
    this.errors = {};
  }

  public check(): void {
    this.valid = Array.from(this.fields.values())
      .map((field) => field.valid)
      .reduce((a, b) => a && b);
    this.disabled = this.valid ? "" : "disabled";
    this.emit("valid", this.valid);
  }

  public validate(): void {
    for (const field of this.fields.values()) {
      field.validate();
    }
    this.check();
  }

  public element(id: string, initial = false): ElementValidatorField {
    const field = new ElementValidatorField(id, this.initial || initial);
    return this.add(id, field);
  }

  public reset(): void {
    for (const [id, field] of this.fields.entries()) {
      field.reset();
      this.errors[id] = "";
    }
  }

  public add<TField extends BaseValidatorField>(
    id: string,
    field: TField
  ): TField {
    this.fields.set(id, field);
    this.errors[id] = "";
    field.on("valid", (errors: Array<string>) => {
      this.check();
      this.errors[id] = errors.join(", ");
    });
    return field;
  }
}
