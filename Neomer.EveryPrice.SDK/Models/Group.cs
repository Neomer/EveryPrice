﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Neomer.EveryPrice.SDK.Models
{
    public class Group : BaseEntity, IGroup
    {

        public virtual string Name { get; set; }

        public virtual IGroup Parent { get; set; }

    }
}
