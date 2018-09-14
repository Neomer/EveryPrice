﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Neomer.EveryPrice.SDK.Models
{
    public interface IShop : IEntity
    {
        string Name { get; set; }

        DateTime CreationDate { get; set; }

        IGroup Group { get; set; }

    }
}
