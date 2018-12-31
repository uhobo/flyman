
import {AbstractControl} from '@angular/forms';

export function duplicateAnswerScaleValidator(control: AbstractControl): {[key: string]: any} | null {
    console.log("duplicateAnswerScaleValidator");
    const forbidden = true;
    return forbidden ? {'duplicateAnswerScale' : {value: control.value}} : null;
}
