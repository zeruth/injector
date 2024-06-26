/*
 * Copyright (c) 2020, ThatGamerBlue <thatgamerblue@gmail.com>
 * All rights reserved.
 *
 * This code is licensed under GPL3, see the complete license in
 * the LICENSE file in the root directory of this submodule.
 */
package nulled.transformers;

import com.openosrs.injector.injection.InjectData;

import com.openosrs.injector.transformers.InjectTransformer;
import net.runelite.asm.ClassFile;
import net.runelite.asm.Method;
import net.runelite.asm.attributes.code.Instruction;
import net.runelite.asm.attributes.code.instructions.InvokeVirtual;

public class EnumInvokeVirtualFixer extends InjectTransformer
{
	private int fixedEnums = 0;
	public EnumInvokeVirtualFixer(InjectData inject)
	{
		super(inject);
	}

	@Override
	public void transformImpl()
	{
		inject.forEachPair(this::fixEnumInvokeVirtuals);
	}

	private void fixEnumInvokeVirtuals(ClassFile rsc, ClassFile vanilla)
	{
		if (vanilla.isEnum()) {
			Method valuesMethod = vanilla.findMethod("values");
			if (valuesMethod != null) {
				for (Instruction insn : valuesMethod.getCode().getInstructions()) {
					if (insn instanceof InvokeVirtual) {
						InvokeVirtual invokeVirtual = (InvokeVirtual) insn;
						invokeVirtual.getMethod().getClazz().fixEnum();
						fixedEnums++;
					}
				}
			}
		}
	}
}