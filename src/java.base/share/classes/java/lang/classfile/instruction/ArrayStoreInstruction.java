/*
 * Copyright (c) 2022, 2024, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */
package java.lang.classfile.instruction;

import java.lang.classfile.CodeBuilder;
import java.lang.classfile.CodeElement;
import java.lang.classfile.CodeModel;
import java.lang.classfile.Instruction;
import java.lang.classfile.Opcode;
import java.lang.classfile.TypeKind;

import jdk.internal.classfile.impl.AbstractInstruction;
import jdk.internal.classfile.impl.Util;

/**
 * Models an array store instruction in the {@code code} array of a {@code Code}
 * attribute.  Corresponding opcodes have a {@linkplain Opcode#kind() kind}
 * of {@link Opcode.Kind#ARRAY_STORE}.  Delivered as a {@link CodeElement} when
 * traversing the elements of a {@link CodeModel}.
 * <p>
 * An array store instruction is composite:
 * {@snippet lang=text :
 * // @link substring="ArrayStoreInstruction" target="CodeBuilder#arrayStore(TypeKind)" :
 * ArrayStoreInstruction(TypeKind typeKind) // @link substring="typeKind" target="#typeKind"
 * }
 * where {@code typeKind} is not {@link TypeKind#VOID void}, and {@link
 * TypeKind#BOOLEAN boolean} is converted to {@link TypeKind#BYTE byte}.
 *
 * @see Opcode.Kind#ARRAY_STORE
 * @see CodeBuilder#arrayStore CodeBuilder::arrayStore
 * @since 24
 */
public sealed interface ArrayStoreInstruction extends Instruction
        permits AbstractInstruction.UnboundArrayStoreInstruction {
    /**
     * {@return the component type of the array}  The {@link TypeKind#BYTE byte}
     * type store instruction {@link Opcode#BASTORE bastore} also operates on
     * {@link TypeKind#BOOLEAN boolean} arrays, so this never returns
     * {@code boolean}.
     */
    TypeKind typeKind();

    /**
     * {@return an array store instruction}
     *
     * @param op the opcode for the specific type of array store instruction,
     *           which must be of kind {@link Opcode.Kind#ARRAY_STORE}
     * @throws IllegalArgumentException if the opcode kind is not
     *         {@link Opcode.Kind#ARRAY_STORE}
     */
    static ArrayStoreInstruction of(Opcode op) {
        Util.checkKind(op, Opcode.Kind.ARRAY_STORE);
        return new AbstractInstruction.UnboundArrayStoreInstruction(op);
    }
}
